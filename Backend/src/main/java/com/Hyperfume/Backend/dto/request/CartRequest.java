package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    @NotNull
    Integer variantId;

    @Min(value = 1, message = "QUANTITY_INVALID")
    Integer quantity;
}
