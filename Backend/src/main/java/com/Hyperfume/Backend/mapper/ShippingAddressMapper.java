package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.dto.response.ShippingResponse;
import com.Hyperfume.Backend.entity.ShippingAddress;
import com.Hyperfume.Backend.entity.ShippingMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShippingAddressMapper {
    @Mapping(target = "user.id", source = "userId")
    ShippingAddress toEntity(ShippingAddressRequest request);

    ShippingAddressResponse toResponse(ShippingAddress shippingAddress);

    void updateShippingAddress(@MappingTarget ShippingAddress shippingAddress, ShippingAddressRequest request);
}
