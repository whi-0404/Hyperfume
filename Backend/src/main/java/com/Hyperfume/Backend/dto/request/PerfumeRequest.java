package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PerfumeRequest {
    String name;

    @NotNull
    Integer brandId;

    String type;
    String perfume_description;
    String perfume_gender;
    String concentration;

    @NotNull
    Integer screntFamilyId;

    String main_notes;
    String longevity;
    String sillage;
    String style;
    String season_usage;
    Integer release_year;

    @NotNull
    Integer countryId;

    String top_notes;
    String middle_notes;
    String base_notes;

//    boolean flash_sale;
}
