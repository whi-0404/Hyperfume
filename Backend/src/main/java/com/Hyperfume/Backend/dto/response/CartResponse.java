package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Integer cartId;

    Integer userId;

    Integer perfumeVariant;

    String imageData;

    String variantName;

    String perfumeName;

    Integer quantity;

    BigDecimal price;

    BigDecimal totalPrice;

    double discount;
}
