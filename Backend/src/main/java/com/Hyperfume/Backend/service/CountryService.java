package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.CountryRequest;
import com.Hyperfume.Backend.dto.response.CountryResponse;

import java.util.List;

public interface CountryService {
    CountryResponse createCountry(CountryRequest request);
    List<CountryResponse> getCountries();
    CountryResponse getCountry(Integer countryId);
    CountryResponse updateCountry(Integer countryId, CountryRequest request);
    void deleteCountry(Integer countryId);
}
