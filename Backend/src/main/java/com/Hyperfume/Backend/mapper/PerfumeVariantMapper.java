package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import org.mapstruct.MappingTarget;

public interface PerfumeVariantMapper {

    PerfumeVariant toPerfumeVariant(PerfumeVariantRequest request);

    PerfumeVariantResponse toResponse(PerfumeVariant variant);

    void updatePerfumeVariant(@MappingTarget PerfumeVariant variant, PerfumeVariantRequest request);
}
