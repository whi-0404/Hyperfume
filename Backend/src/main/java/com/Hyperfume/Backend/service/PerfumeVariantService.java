package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;

import java.util.List;

public interface PerfumeVariantService {
    PerfumeVariantResponse addVariant(PerfumeVariantRequest request);
    List<PerfumeVariantResponse> getVariantsByPerfumeId(Integer perfumeId);
    PerfumeVariantResponse updateVariant(Integer variantId, PerfumeVariantRequest request);
    void deleteVariant(Integer variantId);
    void reduceStock(Integer variantId, Integer quality);
}
