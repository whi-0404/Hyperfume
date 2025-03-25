package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.response.BrandResponse;

public interface BrandService {
    BrandResponse createBrand(BrandRequest request);

    List<BrandResponse> getBrands();

    BrandResponse getBrand(Integer brandId);

    BrandResponse updateBrand(Integer brandId, BrandRequest request);

    void deleteBrand(Integer brandId);
}
