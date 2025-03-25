package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, List<OrderItemRequest> orderItemRequests);
}
