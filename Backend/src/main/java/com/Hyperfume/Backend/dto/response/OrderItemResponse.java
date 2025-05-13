package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Integer orderId;

    String perfumeName;

    String perfumeVariantName;

    String imageData;

    BigDecimal variantDiscountPrice;

    Integer quantity;

    BigDecimal totalPrice;
}
