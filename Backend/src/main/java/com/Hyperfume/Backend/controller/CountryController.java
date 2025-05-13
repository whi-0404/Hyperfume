package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.CountryRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.CountryResponse;
import com.Hyperfume.Backend.service.CountryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CountryController {
    CountryService countryService;

    @PostMapping
    ApiResponse<CountryResponse> createCountry(@RequestBody @Valid CountryRequest request) {
        return ApiResponse.<CountryResponse>builder()
                .result(countryService.createCountry(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CountryResponse>> getCountries() {

        return ApiResponse.<List<CountryResponse>>builder()
                .result(countryService.getCountries())
                .build();
    }

    @GetMapping("/{countryId}")
    ApiResponse<CountryResponse> getCountry(@PathVariable("countryId") Integer countryId) {
        return ApiResponse.<CountryResponse>builder()
                .result(countryService.getCountry(countryId))
                .build();
    }

    @PutMapping("/{countryId}")
    ApiResponse<CountryResponse> updateCountry(
            @PathVariable("countryId") Integer countryId, @RequestBody CountryRequest request) {
        return ApiResponse.<CountryResponse>builder()
                .result(countryService.updateCountry(countryId, request))
                .build();
    }

    @DeleteMapping("/{countryId}")
    ApiResponse<String> deleteCountry(@PathVariable Integer countryId) {
        countryService.deleteCountry(countryId);
        return ApiResponse.<String>builder().result("Country has been deleted").build();
    }
}
