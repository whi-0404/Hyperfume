package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(target = "shippingAddress", source = "shipment.shippingAddress.shipAddress")
    @Mapping(target = "shippingAddressId", source = "shipment.shippingAddress.id")
    @Mapping(target = "serviceName", source = "shipment.name")
    @Mapping(target = "shipmentToken", ignore = true)
    ShipmentResponse toResponse(Shipment shipment);
}
