package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.CreateOrderRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.service.OrderService;
import com.Hyperfume.Backend.service.impl.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrderForBuyNow(@RequestBody CreateOrderRequest createOrderRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(createOrderRequest.getOrderRequest(),
                        createOrderRequest.getOrderItemRequests()))
                .build();
    }
}
