package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.Hyperfume.Backend.dto.request.CountryRequest;
import com.Hyperfume.Backend.dto.response.CountryResponse;
import com.Hyperfume.Backend.entity.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountry(CountryRequest request);

    CountryResponse toCountryResponse(Country country);

    void updateCountry(@MappingTarget Country country, CountryRequest request);
}
