package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.order.OrderItemRequest;
import com.Hyperfume.Backend.dto.response.OrderItemResponse;
import com.Hyperfume.Backend.entity.OrderItem;

public interface OrderItemMapper {

    OrderItem toEntity(OrderItemRequest request);

    OrderItemResponse toResponse(OrderItem orderItem);
}
