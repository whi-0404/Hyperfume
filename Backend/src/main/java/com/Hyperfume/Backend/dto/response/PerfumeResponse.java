package com.Hyperfume.Backend.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerfumeResponse {
    int id;
    String name;

    String brandName;

    String type;
    String perfume_description;
    String perfume_gender;
    String concentration;

    String screntFamilyName;

    String main_notes;
    String longevity;
    String sillage;
    String style;
    String season_usage;
    int release_year;

    String countryName;

    String top_notes;
    String middle_notes;
    String base_notes;

    boolean sale;
    boolean flash_sale;
    int sold;

    double discount;

    LocalDate createdAt;
    LocalDate updatedAt;

    List<PerfumeVariantResponse> perfumeVariantResponseList;
    List<PerfumeImageResponse> perfumeImageResponseList;
}
