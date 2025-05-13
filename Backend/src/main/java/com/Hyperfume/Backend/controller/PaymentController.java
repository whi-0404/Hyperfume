package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
    VNPayService vnPayService;

    @PostMapping("/create-payment")
    public ApiResponse<String> createPayment(@RequestParam int orderId, HttpServletRequest request){
        String paymentUrl = vnPayService.createPaymentUrl(orderId, request);

        return ApiResponse.<String>builder()
                .result(paymentUrl)
                .build();
    }

    @GetMapping("/vnpay-callback")
    public ApiResponse<String> vnPayCallback(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
            }
        }

        boolean success = vnPayService.processPaymentCallback(fields);

        return ApiResponse.<String>builder()
                .result(success ? "successful": "failed")
                .build();
    }
}
