package com.Hyperfume.Backend.controller;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.service.ShipmentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentController {

    ShipmentService shipmentService;

    @GetMapping
    public ApiResponse<ShipmentResponse> getShipmentInfo(
            @RequestParam int shippingAddressId, @RequestParam int quantity) {
        return ApiResponse.<ShipmentResponse>builder()
                .result(shipmentService.getShipmentOrderInfo(shippingAddressId, quantity))
                .build();
    }
}
