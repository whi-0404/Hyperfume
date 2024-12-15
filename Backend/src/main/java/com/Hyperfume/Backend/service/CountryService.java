package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.CountryRequest;
import com.Hyperfume.Backend.dto.response.CountryResponse;
import com.Hyperfume.Backend.entity.Country;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.CountryMapper;
import com.Hyperfume.Backend.repository.CountryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CountryService {
    CountryRepository countryRepository;
    CountryMapper countryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CountryResponse createCountry(CountryRequest request){
        if(countryRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.COUNTRY_EXISTED);

        Country country = countryMapper.toCountry(request);

        return countryMapper.toCountryResponse(countryRepository.save(country));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CountryResponse> getCountries()
    {

        return countryRepository.findAll().stream()
                .map(countryMapper::toCountryResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CountryResponse getCountry(Integer countryId)
    {
        return countryMapper.toCountryResponse(countryRepository.findById(countryId)
                .orElseThrow(()-> new AppException(ErrorCode.COUNTRY_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CountryResponse updateCountry(Integer countryId, CountryRequest request)
    {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(()->new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        countryMapper.updateCountry(country, request);

        return  countryMapper.toCountryResponse(countryRepository.save(country));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCountry(Integer countryId)
    {
        countryRepository.deleteById(countryId);
    }
}
