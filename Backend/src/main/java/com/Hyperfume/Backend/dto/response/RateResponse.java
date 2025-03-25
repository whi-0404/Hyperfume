package com.Hyperfume.Backend.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateResponse {
    Integer userId;

    String userName;

    Integer rateStar;

    String rateContext;

    LocalDate rateDatetime;
}
