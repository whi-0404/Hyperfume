package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.service.RateService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RateController {
    RateService rateService;

    @PostMapping
    public ApiResponse<RateResponse> addRate(@Valid @RequestBody RateRequest request){

        return ApiResponse.<RateResponse>builder()
                .result(rateService.addRate(request))
                .build();
    }

    @GetMapping("/{perfumeId}")
    public ApiResponse<List<RateResponse>> getRatesByPerfumeId(@PathVariable Integer perfumeId) {

        return ApiResponse.<List<RateResponse>>builder()
                .result(rateService.getRatesByPerfumeId(perfumeId))
                .build();
    }
}
