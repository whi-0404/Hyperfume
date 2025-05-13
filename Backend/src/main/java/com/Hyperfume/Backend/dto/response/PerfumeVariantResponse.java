package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
