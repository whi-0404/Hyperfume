package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateRequest {
    @NotNull
    Integer perfumeId;

    @NotNull
    @Min(value = 0, message = "STAR_RATE_INVALID")
    @Max(value = 5, message = "STAR_RATE_INVALID")
    Integer rateStar;

    String rateContext;
}
