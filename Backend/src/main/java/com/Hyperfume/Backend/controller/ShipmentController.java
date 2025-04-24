package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.ShipmentRequest;
import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentController {

    ShipmentService shipmentService;

    @GetMapping
    public ApiResponse<ShipmentResponse> getShipments(@RequestBody @Valid ShipmentRequest request){
        return ApiResponse.<ShipmentResponse>builder()
                .result(shipmentService.getShipmentOrderInfos(request))
                .build();
    }
}
