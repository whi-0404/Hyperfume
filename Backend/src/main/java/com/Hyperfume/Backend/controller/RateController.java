package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.service.RateService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/perfumes/{perfumeId}/rates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RateController {
    RateService rateService;

    @PostMapping
    public ApiResponse<RateResponse> addRate(@Valid @RequestBody RateRequest request) {

        return ApiResponse.<RateResponse>builder()
                .result(rateService.addRate(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RateResponse>> getRatesByPerfumeId(@PathVariable Integer perfumeId) {

        return ApiResponse.<List<RateResponse>>builder()
                .result(rateService.getRatesByPerfumeId(perfumeId))
                .build();
    }

    @PutMapping
    public ApiResponse<String> updateRate(@Valid @RequestBody RateRequest request) {
        rateService.updateRate(request);
        return ApiResponse.<String>builder().result("Rate has been update").build();
    }
}
