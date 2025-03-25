package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.response.BrandResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.BrandMapper;
import com.Hyperfume.Backend.repository.BrandRepository;
import com.Hyperfume.Backend.service.BrandService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse createBrand(BrandRequest request) {
        if (brandRepository.existsByName(request.getName())) throw new AppException(ErrorCode.BRAND_EXISTED);

        Brand brand = brandMapper.toBrand(request);

        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    public List<BrandResponse> getBrands() {

        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    public BrandResponse getBrand(Integer brandId) {
        return brandMapper.toBrandResponse(
                brandRepository.findById(brandId).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse updateBrand(Integer brandId, BrandRequest request) {
        Brand brand =
                brandRepository.findById(brandId).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brandMapper.updateBrand(brand, request);

        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Integer brandId) {
        brandRepository.deleteById(brandId);
    }
}
