package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.service.ShippingAddressService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users/shipping_address")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingAddressController {
    ShippingAddressService shippingAddressService;

    @PostMapping
    ApiResponse<ShippingAddressResponse> createShippingAddress(@RequestBody @Valid ShippingAddressRequest request) {
        return ApiResponse.<ShippingAddressResponse>builder()
                .result(shippingAddressService.createShippingAddress(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShippingAddressResponse>> getUserShippingAddresses() {
        return ApiResponse.<List<ShippingAddressResponse>>builder()
                .result(shippingAddressService.getUserShippingAddresses())
                .build();
    }

    @PutMapping("/{shippingAddressId}")
    ApiResponse<ShippingAddressResponse> updateShippingAddress(
            @PathVariable("shippingAddressId") Integer shippingAddressId,
            @RequestBody @Valid ShippingAddressRequest request) {
        return ApiResponse.<ShippingAddressResponse>builder()
                .result(shippingAddressService.updateShippingAddress(shippingAddressId, request))
                .build();
    }

    @DeleteMapping("/{shippingAddressId}")
    ApiResponse<String> deleteShippingAddress(@PathVariable("shippingAddressId") Integer shippingAddressId) {
        shippingAddressService.deleteShippingAddress(shippingAddressId);
        return ApiResponse.<String>builder()
                .result("Shipping Address has been deleted")
                .build();
    }

    @PutMapping("/setDefault/{shippingAddressId}")
    ApiResponse<String> setDefaultShippingAddress(@PathVariable("shippingAddressId") Integer shippingAddressId) {
        shippingAddressService.setDefaultShippingAddress(shippingAddressId);

        return ApiResponse.<String>builder()
                .result("Shipping Address has been set as default")
                .build();
    }
}
