package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PerfumeGetAllResponse {
    int id;
    String name;

    String brandName;

    String type;
    String perfume_gender;
    String concentration;
    String longevity;

    String screntFamilyName;

    String countryName;

    boolean sale;
    boolean flash_sale;
    int sold;

    double discount;

    BigDecimal min_price;
    BigDecimal max_price;

    String ThumbnailImageUrl;
}
