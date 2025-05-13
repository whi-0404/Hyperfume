package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;
import com.Hyperfume.Backend.entity.PaymentMethod;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodRequest request);

    PaymentMethodResponse toResponse(PaymentMethod paymentMethod);

    void updatePaymentMethod(@MappingTarget PaymentMethod paymentMethod, PaymentMethodRequest request);
}
