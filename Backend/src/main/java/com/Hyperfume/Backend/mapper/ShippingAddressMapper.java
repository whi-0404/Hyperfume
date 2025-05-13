package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.entity.ShippingAddress;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShippingAddressMapper {
    ShippingAddress toEntity(ShippingAddressRequest request);

    ShippingAddressResponse toResponse(ShippingAddress shippingAddress);

    void updateShippingAddress(@MappingTarget ShippingAddress shippingAddress, ShippingAddressRequest request);
}
