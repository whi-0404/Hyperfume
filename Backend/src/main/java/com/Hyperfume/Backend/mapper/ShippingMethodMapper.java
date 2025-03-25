package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ShippingResponse;
import com.Hyperfume.Backend.entity.ShippingMethod;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShippingMethodMapper {
    ShippingMethod toEntity(ShippingRequest request);

    ShippingResponse toResponse(ShippingMethod shippingMethod);

    void updateShippingMethod(@MappingTarget ShippingMethod shippingMethod, ShippingRequest request);
}
