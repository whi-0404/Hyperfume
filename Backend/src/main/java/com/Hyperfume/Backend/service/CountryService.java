package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.CountryRequest;
import com.Hyperfume.Backend.dto.response.CountryResponse;

public interface CountryService {
    CountryResponse createCountry(CountryRequest request);

    List<CountryResponse> getCountries();

    CountryResponse getCountry(Integer countryId);

    CountryResponse updateCountry(Integer countryId, CountryRequest request);

    void deleteCountry(Integer countryId);
}
