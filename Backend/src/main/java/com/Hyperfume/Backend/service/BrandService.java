package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand(BrandRequest request);
    List<BrandResponse> getBrands();
    BrandResponse getBrand(Integer brandId);
    BrandResponse updateBrand(Integer brandId, BrandRequest request);
    void deleteBrand(Integer brandId);
}
