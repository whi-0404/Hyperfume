package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    String screntFamilyName;

    String countryName;

    boolean sale;
    boolean flash_sale;
    int sold;

    LocalDate createdAt;
    LocalDate updatedAt;

    List<PerfumeVariantResponse> perfumeVariantResponseList;
    String ThumbnailImageData;
}
