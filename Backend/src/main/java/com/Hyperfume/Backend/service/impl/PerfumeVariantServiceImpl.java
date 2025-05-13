package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.Hyperfume.Backend.ElasticSearch.ESPerfumeService;
import com.Hyperfume.Backend.entity.Perfume;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeVariantMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.PerfumeVariantRepository;
import com.Hyperfume.Backend.service.PerfumeVariantService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeVariantServiceImpl implements PerfumeVariantService {

    PerfumeVariantRepository perfumeVariantRepository;
    PerfumeVariantMapper perfumeVariantMapper;
    PerfumeRepository perfumeRepository;
    ESPerfumeService esPerfumeService;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeVariantResponse addVariant(PerfumeVariantRequest request) {

        Perfume perfume = perfumeRepository.findById(request.getPerfumeId())
                .orElseThrow(()-> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        PerfumeVariant variant = perfumeVariantMapper.toPerfumeVariant(request);
        PerfumeVariant savedVariant = perfumeVariantRepository.save(variant);

        perfume.getVariants().add(savedVariant);

        esPerfumeService.indexPerfume(perfume);

        return perfumeVariantMapper.toResponse(savedVariant);
    }

    public List<PerfumeVariantResponse> getVariantsByPerfumeId(Integer perfumeId) {
        if (!perfumeRepository.existsById(perfumeId)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        List<PerfumeVariant> variants = perfumeVariantRepository.findByPerfumeId(perfumeId);
        return variants.stream().map(perfumeVariantMapper::toResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeVariantResponse updateVariant(Integer variantId, PerfumeVariantRequest request) {
        PerfumeVariant existingVariant = perfumeVariantRepository
                .findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        perfumeVariantMapper.updatePerfumeVariant(existingVariant, request);

        PerfumeVariant savedVariant = perfumeVariantRepository.save(existingVariant);

        Perfume perfume = perfumeRepository.findById(request.getPerfumeId())
                .orElseThrow(()-> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        esPerfumeService.indexPerfume(perfume);

        return perfumeVariantMapper.toResponse(savedVariant);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVariant(Integer variantId) {
        PerfumeVariant variant =  perfumeVariantRepository.findById(variantId)
                        .orElseThrow(()-> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        perfumeVariantRepository.deleteById(variantId);

        esPerfumeService.indexPerfume(variant.getPerfume());
    }

    @Transactional
    public void reduceStock(Integer variantId, Integer quality) {
        PerfumeVariant variant = perfumeVariantRepository
                .findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        if (variant.getPerfume_stock_quantity() < quality) {
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        variant.setPerfume_stock_quantity(variant.getPerfume_stock_quantity() - quality);

        perfumeVariantRepository.save(variant);
    }
}
