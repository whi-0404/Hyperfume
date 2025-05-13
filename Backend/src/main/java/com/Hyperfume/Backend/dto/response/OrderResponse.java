package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    String notes;

    Integer paymentMethodId;

    BigDecimal totalMoney;

    LocalDate orderDate;

    List<OrderItemResponse> orderItemResponses;

    ShipmentResponse shipmentResponse;
}
