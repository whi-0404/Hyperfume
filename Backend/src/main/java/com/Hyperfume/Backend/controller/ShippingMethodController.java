package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ShippingResponse;
import com.Hyperfume.Backend.service.ShippingMethodService;
import com.Hyperfume.Backend.service.impl.ShippingMethodServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping_methods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingMethodController {
    ShippingMethodService shippingMethodService;

    @PostMapping
    ApiResponse<ShippingResponse> createShippingMethod(@RequestBody @Valid ShippingRequest request)
    {
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingMethodService.createShippingMethod(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShippingResponse>> getShippingMethods()
    {

        return ApiResponse.<List<ShippingResponse>>builder()
                .result(shippingMethodService.getShippingMethods())
                .build();
    }

    @GetMapping("/{shippingId}")
    ApiResponse<ShippingResponse> getShippingMethod(@PathVariable("shippingId") Integer shippingId)
    {
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingMethodService.getShippingMethod(shippingId))
                .build();
    }

    @PutMapping("/{shippingId}")
    ApiResponse<ShippingResponse> updateShippingMethod(@PathVariable("shippingId") Integer shippingId, @RequestBody @Valid ShippingRequest request)
    {
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingMethodService.updateShippingMethod(shippingId, request))
                .build();
    }

    @DeleteMapping("/{shippingId}")
    ApiResponse<String> deleteShippingMethod(@PathVariable Integer shippingId){
        shippingMethodService.deleteShippingMethod(shippingId);
        return ApiResponse.<String>builder()
                .result("Shipping Method has been deleted")
                .build();
    }
}
