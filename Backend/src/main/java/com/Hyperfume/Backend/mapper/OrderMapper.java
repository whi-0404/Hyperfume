package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "shippingAddress.id", source = "shippingAddressId")
    @Mapping(target = "shippingMethod.id", source = "shippingMethodId")
    @Mapping(target = "paymentMethod.id", source = "paymentMethodId")
    Order toEntity(OrderRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "shippingAddressId", source = "shippingAddress.id")
    @Mapping(target = "shippingMethodId", source = "shippingMethod.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    OrderResponse toResponse(Order order);


}
