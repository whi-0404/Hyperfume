package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;

public interface PerfumeVariantService {
    PerfumeVariantResponse addVariant(PerfumeVariantRequest request);

    List<PerfumeVariantResponse> getVariantsByPerfumeId(Integer perfumeId);

    PerfumeVariantResponse updateVariant(Integer variantId, PerfumeVariantRequest request);

    void deleteVariant(Integer variantId);

    void reduceStock(Integer variantId, Integer quality);
}
