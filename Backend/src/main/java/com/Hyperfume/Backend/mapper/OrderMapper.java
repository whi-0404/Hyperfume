package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.Hyperfume.Backend.dto.request.order.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.entity.Order;

public interface OrderMapper {
    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order order);
}
