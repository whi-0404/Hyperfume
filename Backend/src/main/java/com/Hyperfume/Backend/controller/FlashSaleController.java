package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.FlashSaleRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.FlashSaleResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.service.FlashSaleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flash-sales")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FlashSaleController {
    FlashSaleService flashSaleService;

    @GetMapping("/active")
    public ApiResponse<FlashSaleResponse> getActiveFlashSale() {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.getActiveFlashSale())
                .build();
    }

    @GetMapping("/upcoming")
    public ApiResponse<PageResponse<FlashSaleResponse>> getUpcomingFlashSales(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<PageResponse<FlashSaleResponse>>builder()
                .result(flashSaleService.getUpcomingFlashSales(page, size))
                .build();
    }

    @GetMapping("/past")
    public ApiResponse<PageResponse<FlashSaleResponse>> getPastFlashSales(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ApiResponse.<PageResponse<FlashSaleResponse>>builder()
                .result(flashSaleService.getPastFlashSales(page, size))
                .build();
    }

    @GetMapping("/{flashSaleId}")
    public ApiResponse<FlashSaleResponse> getFlashSaleById(@PathVariable int flashSaleId) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.getFlashSaleById(flashSaleId))
                .build();
    }

    @PostMapping
    public ApiResponse<FlashSaleResponse> createFlashSale(@RequestBody @Valid FlashSaleRequest request) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.createFlashSale(request))
                .build();
    }

    @PutMapping("/{flashSaleId}")
    public ApiResponse<FlashSaleResponse> updateFlashSale(
            @PathVariable int flashSaleId, @RequestBody @Valid FlashSaleRequest request) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.updateFlashSale(flashSaleId, request))
                .build();
    }

    @PutMapping("/{flashSaleId}/status")
    public ApiResponse<FlashSaleResponse> toggleFlashSaleStatus(
            @PathVariable int flashSaleId, @RequestParam boolean active) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.toggleFlashSaleStatus(flashSaleId, active))
                .build();
    }

    @PostMapping("/{flashSaleId}/perfumes")
    public ApiResponse<FlashSaleResponse> addPerfumeToFlashSale(
            @PathVariable int flashSaleId,
            @RequestParam int perfumeId,
            @RequestParam int quantityLimit,
            @RequestParam(required = false) Double specialDiscount) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.addPerfumeToFlashSale(flashSaleId, perfumeId, quantityLimit, specialDiscount))
                .build();
    }

    @DeleteMapping("/{flashSaleId}/perfumes/{flashSaleItemId}")
    public ApiResponse<FlashSaleResponse> removePerfumeFromFlashSale(
            @PathVariable int flashSaleId, @PathVariable int flashSaleItemId) {
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.removePerfumeFromFlashSale(flashSaleId, flashSaleItemId))
                .build();
    }
}
