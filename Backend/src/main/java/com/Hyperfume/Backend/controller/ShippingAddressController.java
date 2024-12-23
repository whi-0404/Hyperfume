package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.service.PaymentMethodService;
import com.Hyperfume.Backend.service.ShippingAddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/shipping_address")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingAddressController {
    ShippingAddressService shippingAddressService;

    @PostMapping
    ApiResponse<ShippingAddressResponse> createShippingAddress(@RequestBody @Valid ShippingAddressRequest request)
    {
        return ApiResponse.<ShippingAddressResponse>builder()
                .result(shippingAddressService.createShippingAddress(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShippingAddressResponse>> getUserShippingAddress()
    {
        return ApiResponse.<List<ShippingAddressResponse>>builder()
                .result(shippingAddressService.getUserShippingAddress())
                .build();
    }

//    @GetMapping("/{paymentId}")
//    ApiResponse<PaymentMethodResponse> getPaymentMethod(@PathVariable("paymentId") Integer paymentId)
//    {
//        return ApiResponse.<PaymentMethodResponse>builder()
//                .result(paymentMethodService.getPaymentMethod(paymentId))
//                .build();
//    }

    @PutMapping("/{shippingAddressId}")
    ApiResponse<ShippingAddressResponse> updateShippingAddress(@PathVariable("shippingAddressId") Integer shippingAddressId,
                                                           @RequestBody @Valid ShippingAddressRequest request)
    {
        return ApiResponse.<ShippingAddressResponse>builder()
                .result(shippingAddressService.updateShippingAddress(shippingAddressId, request))
                .build();
    }

    @DeleteMapping("/{shippingAddressId}")
    ApiResponse<String> deleteShippingAddress(@PathVariable("shippingAddressId") Integer shippingAddressId){
        shippingAddressService.deleteShippingAddress(shippingAddressId);
        return ApiResponse.<String>builder()
                .result("Shipping Address has been deleted")
                .build();
    }
}
