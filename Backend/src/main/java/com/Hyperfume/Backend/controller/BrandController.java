package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.BrandResponse;
import com.Hyperfume.Backend.service.BrandService;
import com.Hyperfume.Backend.service.impl.BrandServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandController {
    BrandService brandService;

    @PostMapping
    ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest request)
    {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<BrandResponse>> getBrands()
    {

        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getBrands())
                .build();
    }

    @GetMapping("/{brandId}")
    ApiResponse<BrandResponse> getBrand(@PathVariable("brandId") Integer brandId)
    {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.getBrand(brandId))
                .build();
    }

    @PutMapping("/{brandId}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable("brandId") Integer brandId, @RequestBody BrandRequest request)
    {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(brandId, request))
                .build();
    }

    @DeleteMapping("/{brandId}")
    ApiResponse<String> deleteBrand(@PathVariable Integer brandId){
        brandService.deleteBrand(brandId);
        return ApiResponse.<String>builder()
                .result("Brand has been deleted")
                .build();
    }
}
