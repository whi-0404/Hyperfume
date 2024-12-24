package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.entity.Country;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.ScrentFamily;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PerfumeMapper {

    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "screntFamilyName", source = "screntFamily.name")
    @Mapping(target = "countryName", source = "country.name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "sold", source = "sold")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "release_year", source = "release_year")
    PerfumeResponse toResponse(Perfume perfume);

    // Ánh xạ từ PerfumeRequest sang Perfume
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "screntFamily", source = "screntFamily")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "release_year", source = "request.release_year")
    Perfume toEntity(PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country);

    // Cập nhật Perfume từ PerfumeRequest
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "screntFamily", source = "screntFamily")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "perfume.release_year", source = "request.release_year")
    void updateEntity(@MappingTarget Perfume perfume, PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country);
}
