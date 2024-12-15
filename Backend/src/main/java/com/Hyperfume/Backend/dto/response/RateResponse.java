package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateResponse {
    Integer id;

    Integer userId;

    Integer perfumeId;

    Integer rateStar;

    String rateContext;

    LocalDate rateDatetime;
}
