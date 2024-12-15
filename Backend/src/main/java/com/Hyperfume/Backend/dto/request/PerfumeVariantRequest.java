package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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

    @DecimalMin(value = "0.0", inclusive = true, message = "DISCOUNT_INVALID")
    @DecimalMax(value = "100.0", inclusive = true, message = "DISCOUNT_INVALID")
    Double discount;

    @Min(value = 0, message = "STOCK_INVALID")
    int perfume_stock_quantity;
}
