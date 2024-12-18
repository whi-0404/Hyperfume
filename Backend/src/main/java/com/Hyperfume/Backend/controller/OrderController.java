package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.CreateOrderRequest;
import com.Hyperfume.Backend.dto.request.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.OrderRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
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
