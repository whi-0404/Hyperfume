package com.Hyperfume.Backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerfumeVariantRequest {
    String name;
    int perfumeId;

    @DecimalMin(value = "0.0", inclusive = true, message = "PRICE_INVALID")
    BigDecimal price;

    @Min(value = 0, message = "STOCK_INVALID")
    int perfume_stock_quantity;
}
