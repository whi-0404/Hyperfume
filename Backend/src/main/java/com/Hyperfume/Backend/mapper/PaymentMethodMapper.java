package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.BrandRequest;
import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodRequest request);

    PaymentMethodResponse toResponse(PaymentMethod paymentMethod);

    void updatePaymentMethod(@MappingTarget PaymentMethod paymentMethod, PaymentMethodRequest request);
}
