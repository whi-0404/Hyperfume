package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;
import com.Hyperfume.Backend.service.PaymentMethodService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment_method")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentMethodController {
    PaymentMethodService paymentMethodService;

    @PostMapping
    ApiResponse<PaymentMethodResponse> createPaymentMethod(@RequestBody @Valid PaymentMethodRequest request) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.createPaymentMethod(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PaymentMethodResponse>> getPaymentMethods() {

        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .result(paymentMethodService.getPaymentMethods())
                .build();
    }

    @GetMapping("/{paymentId}")
    ApiResponse<PaymentMethodResponse> getPaymentMethod(@PathVariable("paymentId") Integer paymentId) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.getPaymentMethod(paymentId))
                .build();
    }

    @PutMapping("/{paymentId}")
    ApiResponse<PaymentMethodResponse> updatePaymentMethod(
            @PathVariable("paymentId") Integer paymentId, @RequestBody PaymentMethodRequest request) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.updatePaymentMethod(paymentId, request))
                .build();
    }

    @DeleteMapping("/{paymentId}")
    ApiResponse<String> deletePaymentMethod(@PathVariable Integer paymentId) {
        paymentMethodService.deletePaymentMethod(paymentId);
        return ApiResponse.<String>builder()
                .result("Payment method has been deleted")
                .build();
    }
}
