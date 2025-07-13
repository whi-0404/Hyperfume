package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.order.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.entity.Order;

public interface OrderMapper {
    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order order);
}
