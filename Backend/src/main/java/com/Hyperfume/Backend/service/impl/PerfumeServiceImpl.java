package com.Hyperfume.Backend.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeMapper;
import com.Hyperfume.Backend.repository.BrandRepository;
import com.Hyperfume.Backend.repository.CountryRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.ScrentFamilyRepository;
import com.Hyperfume.Backend.repository.specification.PerfumeSpecification;
import com.Hyperfume.Backend.service.PerfumeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeServiceImpl implements PerfumeService {

    PerfumeRepository perfumeRepository;
    BrandRepository brandRepository;
    ScrentFamilyRepository screntFamilyRepository;
    CountryRepository countryRepository;

    PerfumeMapper perfumeMapper;

    // Lấy danh sách nước hoa
    public PageResponse<PerfumeGetAllResponse> getAllPerfumes(
            int page,
            int size,
            String sortOption,
            String gender,
            String longevity,
            Integer countryId,
            Integer brandId,
            String concentration,
            Integer screntFamilyId,
            Long maxPrice) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Specification<Perfume> spec = PerfumeSpecification.getSpecifications(
                gender, longevity, countryId, brandId, concentration, screntFamilyId, maxPrice, sortOption);

        Page<Perfume> pageData = perfumeRepository.findAll(spec, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    // Lấy thông tin nước hoa theo ID
    public PerfumeResponse getPerfumeById(int id) {
        Perfume perfume =
                perfumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));
        return perfumeMapper.toResponse(perfume);
    }

    // Tạo mới nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeResponse createPerfume(PerfumeRequest request) {
        Brand brand = brandRepository
                .findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        ScrentFamily screntFamily = screntFamilyRepository
                .findById(request.getScrentFamilyId())
                .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED));
        Country country = countryRepository
                .findById(request.getCountryId())
                .orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));

        Perfume perfume = perfumeMapper.toEntity(request, brand, screntFamily, country);
        perfume = perfumeRepository.save(perfume);

        return perfumeMapper.toResponse(perfume);
    }

    // Cập nhật nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeResponse updatePerfume(int id, PerfumeRequest request) {
        Perfume perfume =
                perfumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        Brand brand = null;
        if (request.getBrandId() != null) {
            brand = brandRepository
                    .findById(request.getBrandId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        }
        ScrentFamily screntFamily = null;
        if (request.getScrentFamilyId() != null) {
            screntFamily = screntFamilyRepository
                    .findById(request.getScrentFamilyId())
                    .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED));
        }
        Country country = null;
        if (request.getCountryId() != null) {
            country = countryRepository
                    .findById(request.getCountryId())
                    .orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        }
        perfumeMapper.updateEntity(perfume, request, brand, screntFamily, country);
        perfume = perfumeRepository.save(perfume);
        return perfumeMapper.toResponse(perfume);
    }

    // Xóa nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deletePerfume(int id) {
        if (!perfumeRepository.existsById(id)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }
        perfumeRepository.deleteById(id);
    }

    // Flash sale
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void toggleFlashSale(Integer perfumeId, boolean isFlashSale) {
        if (!perfumeRepository.existsById(perfumeId)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        perfumeRepository.updateFlashSaleStatus(perfumeId, isFlashSale);
    }

    public PageResponse<PerfumeGetAllResponse> getFlashSalePerfumes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByFlashSale(pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> getPerfumesByTypeName(String typeName, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByTypeName(typeName, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> getPerfumesByGender(String gender, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByGender(gender, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> getPerfumesByCountry(Integer countryId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByCountryId(countryId, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> getPerfumesByBrand(Integer brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByBrandId(brandId, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> getPerfumesByScrentFamily(Integer screntFamilyId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.findAllByScrentFamilyId(screntFamilyId, pageable);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> searchPerfumesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Perfume> pageData = perfumeRepository.searchByName(name, pageable);

        if (pageData.getContent().isEmpty()) {
            throw new AppException(ErrorCode.NO_FOUND_BY_SEARCH_NAME);
        }

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .Data(pageData.getContent().stream()
                        .map(perfumeMapper::toGetAllPerfumeResponse)
                        .toList())
                .build();
    }
}
