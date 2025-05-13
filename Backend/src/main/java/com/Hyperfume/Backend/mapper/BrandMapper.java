package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.response.BrandResponse;
import com.Hyperfume.Backend.entity.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, BrandRequest request);
}
