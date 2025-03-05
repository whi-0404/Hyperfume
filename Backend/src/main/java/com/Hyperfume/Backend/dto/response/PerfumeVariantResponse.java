package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerfumeVariantResponse {
    int id;
    String name;
    int perfumeId;
    BigDecimal price;
    BigDecimal discountedPrice;
    int perfume_stock_quantity;
}
