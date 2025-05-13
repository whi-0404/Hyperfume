package com.Hyperfume.Backend.service.impl;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public FlashSaleResponse getActiveFlashSale() {
        LocalDateTime now = LocalDateTime.now();

        FlashSale flashSale = flashSaleRepository.findActiveFlashSale(now)
                .orElseThrow(()-> new AppException(ErrorCode.ACTIVE_FLASH_SALE_NOT_EXISTED));

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

    @Override
    public PageResponse<FlashSaleResponse> getInactiveCurrentFlashSales(int page, int size) {
        LocalDateTime now = LocalDateTime.now();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").descending());

        Page<FlashSale> pageData = flashSaleRepository.findInactiveCurrentFlashSales(now, pageable);
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
    public FlashSaleResponse getFlashSaleById(int flashSaleId) {
        FlashSale flashSale = flashSaleRepository.findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse createFlashSale(FlashSaleRequest request) {
        FlashSale flashSale = flashSaleMapper.toEntity(request);
        flashSale = flashSaleRepository.save(flashSale);

        if(request.getItems() != null && !request.getItems().isEmpty()){
                List<FlashSaleItem> items = new ArrayList<>();

                for(FlashSaleItemRequest itemRequest : request.getItems()){
                    Perfume perfume = perfumeRepository.findById(itemRequest.getPerfumeId())
                            .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

                    FlashSaleItem item = flashSaleItemMapper.toEntity(itemRequest, flashSale, perfume);
                    items.add(item);

//                    perfume.setFlash_sale(true);
                }
                items = flashSaleItemRepository.saveAll(items);
                flashSale.setFlashSaleItems(items);
        }
        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse updateFlashSale(int flashSaleId, FlashSaleRequest request) {
        FlashSale flashSale = flashSaleRepository.findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        flashSaleMapper.updateEntity(flashSale, request);
        flashSale = flashSaleRepository.save(flashSale);

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse toggleFlashSaleStatus(int flashSaleId, boolean active) {
        FlashSale flashSale = flashSaleRepository.findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        if(active){
            if(!flashSaleRepository.findByIsActiveTrue()){
                flashSale.setActive(true);
            }
            else {
                throw new AppException(ErrorCode.ACTIVE_FLASH_SALE_EXISTED);
            }
        }
        else {
            flashSale.setActive(false);
            flashSale = flashSaleRepository.save(flashSale);
        }
        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse addPerfumeToFlashSale(int flashSaleId, int perfumeId, int quantityLimit, Double specialDiscount) {
        FlashSale flashSale = flashSaleRepository.findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

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

        flashSaleItemRepository.save(item);
        flashSale.getFlashSaleItems().add(item);

        return flashSaleMapper.toResponse(flashSale);
    }

    @Override
    public FlashSaleResponse removePerfumeFromFlashSale(int flashSaleId, int flashSaleItemId) {
        FlashSale flashSale = flashSaleRepository.findById(flashSaleId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));

        FlashSaleItem item = flashSaleItemRepository.findById(flashSaleItemId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_ITEM_NOT_EXISTED));

        if (item.getFlashSale().getId() != flashSaleId) {
            throw new AppException(ErrorCode.FLASH_SALE_ITEM_NOT_IN_FLASH_SALE);
        }

        item.setActive(false);
        flashSaleItemRepository.save(item);

        if(flashSale.isActive()){
            Perfume perfume = item.getPerfume();
            perfume.setFlash_sale(false);
            perfumeRepository.save(perfume);
        }
        return flashSaleMapper.toResponse(flashSale);
    }
}
