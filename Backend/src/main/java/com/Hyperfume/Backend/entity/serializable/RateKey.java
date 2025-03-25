package com.Hyperfume.Backend.entity.serializable;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

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
