package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
import com.Hyperfume.Backend.dto.response.BrandResponse;
import com.Hyperfume.Backend.dto.response.UserResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.entity.Role;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.BrandMapper;
import com.Hyperfume.Backend.mapper.UserMapper;
import com.Hyperfume.Backend.repository.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse createBrand(BrandRequest request){
        if(brandRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.BRAND_EXISTED);

        Brand brand = brandMapper.toBrand(request);

        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    public List<BrandResponse> getBrands()
    {

        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    public BrandResponse getBrand(Integer brandId)
    {
        return brandMapper.toBrandResponse(brandRepository.findById(brandId)
                .orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse updateBrand(Integer brandId, BrandRequest request)
    {
        Brand brand=brandRepository.findById(brandId)
                .orElseThrow(()->new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brandMapper.updateBrand(brand, request);

        return  brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Integer brandId)
    {
        brandRepository.deleteById(brandId);
    }
}
