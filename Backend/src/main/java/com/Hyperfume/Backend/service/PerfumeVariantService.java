package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeVariantMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.PerfumeVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeVariantService {

    PerfumeVariantRepository perfumeVariantRepository;
    PerfumeVariantMapper perfumeVariantMapper;
    PerfumeRepository perfumeRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeVariantResponse addVariant(PerfumeVariantRequest request) {
        if (!perfumeRepository.existsById(request.getPerfumeId())) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        PerfumeVariant variant = perfumeVariantMapper.toPerfumeVariant(request);
        PerfumeVariant savedVariant = perfumeVariantRepository.save(variant);

        if(request.getDiscount()!=null&&request.getDiscount()> 0.0){
            perfumeRepository.updateSaleStatus(request.getPerfumeId(), true);
        }
        return perfumeVariantMapper.toResponse(savedVariant);
    }

    public List<PerfumeVariantResponse> getVariantsByPerfumeId(Integer perfumeId) {
        if(!perfumeRepository.existsById(perfumeId)){
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        List<PerfumeVariant> variants = perfumeVariantRepository.findByPerfumeId(perfumeId);
        return variants.stream()
                .map(perfumeVariantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PerfumeVariantResponse updateVariant(Integer variantId, PerfumeVariantRequest request) {
        PerfumeVariant existingVariant = perfumeVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        perfumeVariantMapper.updatePerfumeVariant(existingVariant, request);

        if(request.getDiscount() != null && request.getDiscount() >0){
            perfumeRepository.updateSaleStatus(request.getPerfumeId(), true);
        }

        return perfumeVariantMapper.toResponse(perfumeVariantRepository.save(existingVariant));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVariant(Integer variantId) {
        if (!perfumeVariantRepository.existsById(variantId)) {
            throw new AppException(ErrorCode.VARIANT_NOT_FOUND);
        }
        perfumeVariantRepository.deleteById(variantId);
    }


    @Transactional
    public void reduceStock(Integer variantId, Integer quality){
        PerfumeVariant variant = perfumeVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        if(variant.getPerfume_stock_quantity()< quality){
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        variant.setPerfume_stock_quantity(variant.getPerfume_stock_quantity()-quality);

        perfumeVariantRepository.save(variant);
    }


}
