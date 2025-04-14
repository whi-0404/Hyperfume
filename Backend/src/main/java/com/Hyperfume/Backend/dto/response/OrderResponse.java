package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;
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

    BigDecimal totalMoney;

    BigDecimal shipCost;

    List<OrderItemResponse> orderItemResponses;
}
