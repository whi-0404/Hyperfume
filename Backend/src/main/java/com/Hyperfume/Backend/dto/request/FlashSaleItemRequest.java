package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleItemRequest {

    @NotNull(message = "Perfume ID cannot be null")
    Integer perfumeId;

    @Positive(message = "Quantity limit must be positive")
    int quantityLimit;

    Double specialDiscountPercentage;
}
