package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.PerfumeVariantRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.service.PerfumeVariantService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/perfumes/{perfumeId}/variants")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PerfumeVariantController {

    PerfumeVariantService perfumeVariantService;

    // 1. Lấy danh sách các phiên bản của một sản phẩm nước hoa
    @GetMapping
    public ApiResponse<List<PerfumeVariantResponse>> getVariants(@PathVariable Integer perfumeId) {
        return ApiResponse.<List<PerfumeVariantResponse>>builder()
                .result(perfumeVariantService.getVariantsByPerfumeId(perfumeId))
                .build();
    }

    // 2. Thêm một phiên bản mới
    @PostMapping
    public ApiResponse<PerfumeVariantResponse> addVariant(@RequestBody @Valid PerfumeVariantRequest request) {
        return ApiResponse.<PerfumeVariantResponse>builder()
                .result(perfumeVariantService.addVariant(request))
                .build();
    }

    // 3. Cập nhật thông tin một phiên bản
    @PutMapping("/{variantId}")
    public ApiResponse<PerfumeVariantResponse> updateVariant(
            @PathVariable Integer variantId, @RequestBody @Valid PerfumeVariantRequest request) {
        return ApiResponse.<PerfumeVariantResponse>builder()
                .result(perfumeVariantService.updateVariant(variantId, request))
                .build();
    }

    // 4. Xóa một phiên bản
    @DeleteMapping("/{variantId}")
    public ApiResponse<String> deleteVariant(@PathVariable Integer variantId) {
        perfumeVariantService.deleteVariant(variantId);
        return ApiResponse.<String>builder().result("Variant has been deleted").build();
    }
}
