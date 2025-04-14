package com.Hyperfume.Backend.controller;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.CreateOrderRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(
                        createOrderRequest.getOrderRequest(), createOrderRequest.getOrderItemRequests()))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .build();
    }
}
