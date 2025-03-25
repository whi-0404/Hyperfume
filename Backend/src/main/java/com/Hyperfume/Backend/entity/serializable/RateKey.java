package com.Hyperfume.Backend.entity.serializable;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class RateKey implements Serializable {
    Integer userId;
    Integer perfumeId;
}
