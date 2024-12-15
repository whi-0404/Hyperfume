package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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
