package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.GHNCreateOrderRequest;
import com.Hyperfume.Backend.service.impl.Shipment.GHNShipmentClient;
import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.order.CreateOrderRequest;
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
    GHNShipmentClient ghnShipmentClient;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(createOrderRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .build();
    }

    @PostMapping("/ghn/create")
    public ApiResponse<String> createOrderGHN(@RequestBody GHNCreateOrderRequest ghnCreateOrderRequest){
        return ApiResponse.<String>builder()
                .result(ghnShipmentClient.createOrder(ghnCreateOrderRequest))
                .build();
    }

    @PostMapping("/ghn/cancel")
    public ApiResponse<String> cancelOrderGHN(@RequestParam String orderCodeGHN){
        return ApiResponse.<String>builder()
                .result(ghnShipmentClient.cancelOrder(orderCodeGHN))
                .build();
    }
}
