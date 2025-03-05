package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.mapper.impl.utils.PerfumeVariantUtil;
import com.Hyperfume.Backend.mapper.PerfumeVariantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PerfumeVariantMapperImpl implements PerfumeVariantMapper {
    private final PerfumeVariantUtil perfumeVariantUtil;

    @Override
    public PerfumeVariant toPerfumeVariant(PerfumeVariantRequest request) {
        if (request == null) {
            return null;
        } else {
            PerfumeVariant.PerfumeVariantBuilder perfumeVariant = PerfumeVariant.builder();
            perfumeVariant.perfume(this.perfumeVariantRequestToPerfume(request));
            perfumeVariant.name(request.getName());
            perfumeVariant.price(request.getPrice());

            perfumeVariant.perfume_stock_quantity(request.getPerfume_stock_quantity());
            return perfumeVariant.build();
        }
    }

    @Override
    public PerfumeVariantResponse toResponse(PerfumeVariant variant) {
        if (variant == null) {
            return null;
        } else {
            PerfumeVariantResponse.PerfumeVariantResponseBuilder perfumeVariantResponse = PerfumeVariantResponse.builder();
            perfumeVariantResponse.perfumeId(this.variantPerfumeId(variant));
            perfumeVariantResponse.id(variant.getId());
            perfumeVariantResponse.name(variant.getName());
            perfumeVariantResponse.price(variant.getPrice());
            perfumeVariantResponse.perfume_stock_quantity(variant.getPerfume_stock_quantity());
            perfumeVariantResponse.discountedPrice(perfumeVariantUtil.calculateDiscountedPrice(variant));
            return perfumeVariantResponse.build();
        }
    }

    @Override
    public void updatePerfumeVariant(PerfumeVariant variant, PerfumeVariantRequest request) {
        if (request != null) {
            if (request.getName() != null) {
                variant.setName(request.getName());
            }

            if (request.getPrice() != null) {
                variant.setPrice(request.getPrice());
            }

            variant.setPerfume_stock_quantity(request.getPerfume_stock_quantity());
        }
    }

    protected Perfume perfumeVariantRequestToPerfume(PerfumeVariantRequest perfumeVariantRequest) {
        if (perfumeVariantRequest == null) {
            return null;
        } else {
            Perfume.PerfumeBuilder perfume = Perfume.builder();
            perfume.id(perfumeVariantRequest.getPerfumeId());
            return perfume.build();
        }
    }

    private int variantPerfumeId(PerfumeVariant perfumeVariant) {
        if (perfumeVariant == null) {
            return 0;
        } else {
            Perfume perfume = perfumeVariant.getPerfume();
            if (perfume == null) {
                return 0;
            } else {
                int id = perfume.getId();
                return id;
            }
        }
    }
}
