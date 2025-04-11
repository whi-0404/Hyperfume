package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleItemResponse {
    int id;

    int perfumeId;

    String perfumeName;

    String perfumeImage;

    BigDecimal originalMinPrice;

    double discountedPrice;

    int quantityLimit;

    int quantitySold;

    double specialDiscountPercentage;

    boolean active;
}
