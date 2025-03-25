package com.Hyperfume.Backend.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer userId;

    String shippingAddressId;

    String notes;

    Integer shippingMethodId;

    Integer paymentMethodId;

    List<OrderItemResponse> orderItemResponses;
}
