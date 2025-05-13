package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Integer cartId;

    Integer userId;

    Integer perfumeVariant;

    String imageUrl;

    String variantName;

    String perfumeName;

    Integer quantity;

    BigDecimal price;

    BigDecimal totalPrice;

    double discount;
}
