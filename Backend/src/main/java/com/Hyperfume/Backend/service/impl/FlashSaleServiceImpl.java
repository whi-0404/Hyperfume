package com.Hyperfume.Backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hyperfume.Backend.dto.request.FlashSaleItemRequest;
import com.Hyperfume.Backend.dto.request.FlashSaleRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.entity.FlashSale;
import com.Hyperfume.Backend.entity.FlashSaleItem;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.FlashSaleItemMapper;
import com.Hyperfume.Backend.mapper.FlashSaleMapper;
import com.Hyperfume.Backend.repository.FlashSaleItemRepository;
import com.Hyperfume.Backend.repository.FlashSaleRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.service.FlashSaleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlashSaleServiceImpl implements FlashSaleService {
    FlashSaleRepository flashSaleRepository;
    FlashSaleItemRepository flashSaleItemRepository;
    FlashSaleMapper flashSaleMapper;
    PerfumeRepository perfumeRepository;
    FlashSaleItemMapper flashSaleItemMapper;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void scheduledFlashSale() {
        LocalDateTime now = LocalDateTime.now();

        // Tắt flash sale đã quá thời gian
        List<FlashSale> toDeactivate = flashSaleRepository.findFlashSalesToDeactivate(now);
        for (FlashSale flashSale : toDeactivate) {
            deactivateFlashSale(flashSale);
        }

        boolean hasActiveFlashSale = flashSaleRepository.existsActiveFlashSale();

        if (!hasActiveFlashSale) {
            List<FlashSale> toActivate = flashSaleRepository.findFlashSalesToActivate(now);
            // Nếu có nhiều flash sale có thể kích hoạt, chỉ kích hoạt cái đầu tiên (sớm nhất)
            if (!toActivate.isEmpty()) {
                activateFlashSale(toActivate.getFirst());
            }
        }
    }

    @Override
    public FlashSaleResponse getActiveFlashSale() {
        FlashSale flashSale = flashSaleRepository
                .findActiveFlashSale()
                .orElseThrow(() -> new AppException(ErrorCode.ACTIVE_FLASH_SALE_NOT_EXISTED));

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public PageResponse<FlashSaleResponse> getUpcomingFlashSales(int page, int size) {
        LocalDateTime now = LocalDateTime.now();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").descending());

        Page<FlashSale> pageData = flashSaleRepository.findUpcomingFlashSales(now, pageable);

        return PageResponse.<FlashSaleResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(flashSaleMapper::toResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<FlashSaleResponse> getPastFlashSales(int page, int size) {
        LocalDateTime now = LocalDateTime.now();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").descending());

        Page<FlashSale> pageData = flashSaleRepository.findPastFlashSales(now, pageable);

        return PageResponse.<FlashSaleResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(flashSaleMapper::toResponse)
                        .toList())
                .build();
    }

    //    @Override
    //    public PageResponse<FlashSaleResponse> getInactiveCurrentFlashSales(int page, int size) {
    //        LocalDateTime now = LocalDateTime.now();
    //
    //        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").descending());
    //
    //        Page<FlashSale> pageData = flashSaleRepository.findInactiveCurrentFlashSales(now, pageable);
    //        return PageResponse.<FlashSaleResponse>builder()
    //                .pageSize(pageData.getSize())
    //                .totalPages(pageData.getTotalPages())
    //                .totalElements(pageData.getTotalElements())
    //                .Data(pageData.getContent().stream()
    //                        .map(flashSaleMapper::toResponse)
    //                        .toList())
    //                .build();
    //    }

    @Override
    public FlashSaleResponse getFlashSaleById(int flashSaleId) {
        FlashSale flashSale = flashSaleRepository
                .findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse createFlashSale(FlashSaleRequest request) {
        validateFlashSaleTiming(request);

        // Kiểm tra xung đột thời gian với flash sale khác
        List<FlashSale> overlappingFlashSales =
                flashSaleRepository.findOverlappingFlashSales(request.getStartTime(), request.getEndTime(), null);

        if (!overlappingFlashSales.isEmpty()) {
            throw new AppException(ErrorCode.FLASH_SALE_TIME_CONFLICT);
        }

        FlashSale flashSale = flashSaleMapper.toEntity(request);
        flashSale = flashSaleRepository.save(flashSale);

        if (request.getItems() != null && !request.getItems().isEmpty()) {
            List<FlashSaleItem> items = new ArrayList<>();

            for (FlashSaleItemRequest itemRequest : request.getItems()) {
                Perfume perfume = perfumeRepository
                        .findById(itemRequest.getPerfumeId())
                        .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

                FlashSaleItem item = flashSaleItemMapper.toEntity(itemRequest, flashSale, perfume);
                items.add(item);

                perfume.setFlash_sale(true);
            }
            items = flashSaleItemRepository.saveAll(items);
            flashSale.setFlashSaleItems(items);
        }
        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse updateFlashSale(int flashSaleId, FlashSaleRequest request) {
        FlashSale flashSale = flashSaleRepository
                .findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        if (request.getStartTime() != null && request.getEndTime() != null) {
            List<FlashSale> overlappingFlashSales = flashSaleRepository.findOverlappingFlashSales(
                    request.getStartTime(), request.getEndTime(), flashSaleId);

            if (!overlappingFlashSales.isEmpty()) {
                throw new AppException(ErrorCode.FLASH_SALE_TIME_CONFLICT);
            }

            validateFlashSaleTiming(request);
        }

        flashSaleMapper.updateEntity(flashSale, request);
        flashSale = flashSaleRepository.save(flashSale);

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse toggleFlashSaleStatus(int flashSaleId, boolean active) {
        FlashSale flashSale = flashSaleRepository
                .findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        if (active) {
            // Kiểm tra thời gian hiện tại có thể kích hoạt flash sale không
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(flashSale.getStartTime()) || now.isAfter(flashSale.getEndTime())) {
                throw new AppException(ErrorCode.FLASH_SALE_OUTSIDE_TIME_WINDOW);
            }

            if (!flashSaleRepository.findByIsActiveTrue()) {
                flashSale.setActive(true);

                for (FlashSaleItem item : flashSale.getFlashSaleItems()) {
                    if (item.isActive()) {
                        Perfume perfume = item.getPerfume();
                        perfume.setFlash_sale(true);
                        perfumeRepository.save(perfume);
                    }
                }

            } else {
                throw new AppException(ErrorCode.ACTIVE_FLASH_SALE_EXISTED);
            }
        } else {
            flashSale.setActive(false);
            flashSale = flashSaleRepository.save(flashSale);

            for (FlashSaleItem item : flashSale.getFlashSaleItems()) {
                if (item.isActive()) {
                    Perfume perfume = item.getPerfume();
                    perfume.setFlash_sale(false);
                    perfumeRepository.save(perfume);
                }
            }
        }
        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    @Transactional
    public FlashSaleResponse addPerfumeToFlashSale(
            int flashSaleId, int perfumeId, int quantityLimit, Double specialDiscount) {
        FlashSale flashSale = flashSaleRepository
                .findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        Perfume perfume = perfumeRepository
                .findById(perfumeId)
                .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        // Kiểm tra xem sản phẩm đã có trong flash sale này chưa
        boolean alreadyExists = flashSale.getFlashSaleItems().stream()
                .anyMatch(item -> item.getPerfume().getId() == perfumeId);

        if (alreadyExists) {
            throw new AppException(ErrorCode.PERFUME_ALREADY_IN_FLASH_SALE);
        }

        FlashSaleItem item = new FlashSaleItem();
        item.setFlashSale(flashSale);
        item.setPerfume(perfume);
        item.setQuantityLimit(quantityLimit);
        item.setSpecialDiscountPercentage(specialDiscount);
        item.setActive(true);

        perfume.setFlash_sale(true);
        perfumeRepository.save(perfume);

        flashSaleItemRepository.save(item);
        flashSale.getFlashSaleItems().add(item);

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse removePerfumeFromFlashSale(int flashSaleId, int flashSaleItemId) {
        FlashSale flashSale = flashSaleRepository
                .findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        FlashSaleItem item = flashSaleItemRepository
                .findById(flashSaleItemId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_ITEM_NOT_EXISTED));

        if (item.getFlashSale().getId() != flashSaleId) {
            throw new AppException(ErrorCode.FLASH_SALE_ITEM_NOT_IN_FLASH_SALE);
        }

        item.setActive(false);
        flashSaleItemRepository.save(item);

        if (flashSale.isActive()) {
            Perfume perfume = item.getPerfume();
            perfume.setFlash_sale(false);
            perfumeRepository.save(perfume);
        }
        return flashSaleMapper.toResponse(flashSale);
    }

    @Transactional
    public boolean incrementFlashSaleItemSold(int flashSaleItemId, int quantity) {
        FlashSaleItem item = flashSaleItemRepository
                .findById(flashSaleItemId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_ITEM_NOT_EXISTED));

        // Kiểm tra xem đã đạt giới hạn số lượng chưa
        if (item.getQuantitySold() + quantity > item.getQuantityLimit()) {
            return false;
        }

        // Tăng số lượng đã bán
        item.setQuantitySold(item.getQuantitySold() + quantity);
        flashSaleItemRepository.save(item);

        // Nếu đã đạt đến giới hạn, tự động hủy kích hoạt sản phẩm trong flash sale
        if (item.getQuantitySold() >= item.getQuantityLimit()) {
            item.setActive(false);
            flashSaleItemRepository.save(item);

            Perfume perfume = item.getPerfume();
            perfume.setFlash_sale(false);
            perfumeRepository.save(perfume);
        }

        return true;
    }

    @Transactional
    public void activateFlashSale(FlashSale flashSale) {
        boolean hasActiveFlashSale = flashSaleRepository.existsActiveFlashSale();
        if (hasActiveFlashSale) {
            return;
        }

        flashSale.setActive(true);
        flashSaleRepository.save(flashSale);

        for (FlashSaleItem item : flashSale.getFlashSaleItems()) {
            if (item.isActive()) {
                Perfume perfume = item.getPerfume();
                perfume.setFlash_sale(true);
                perfumeRepository.save(perfume);
            }
        }
    }

    @Transactional
    public void deactivateFlashSale(FlashSale flashSale) {
        flashSale.setActive(false);
        flashSaleRepository.save(flashSale);

        for (FlashSaleItem item : flashSale.getFlashSaleItems()) {
            Perfume perfume = item.getPerfume();
            perfume.setFlash_sale(false);
            perfumeRepository.save(perfume);
        }
    }

    private void validateFlashSaleTiming(FlashSaleRequest request) {
        LocalDateTime now = LocalDateTime.now();

        // Kiểm tra thời gian bắt đầu phải trong tương lai
        if (request.getStartTime().isBefore(now)) {
            throw new AppException(ErrorCode.FLASH_SALE_START_TIME_IN_PAST);
        }

        // Kiểm tra thời gian kết thúc phải sau thời gian bắt đầu
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new AppException(ErrorCode.FLASH_SALE_END_TIME_BEFORE_START);
        }
    }
}
