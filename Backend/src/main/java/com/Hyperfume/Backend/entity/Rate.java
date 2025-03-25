package com.Hyperfume.Backend.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import com.Hyperfume.Backend.entity.serializable.RateKey;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rates")
public class Rate {
    @EmbeddedId
    RateKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @ManyToOne
    @MapsId("perfumeId")
    @JoinColumn(name = "perfume_id", insertable = false, updatable = false)
    Perfume perfume;

    @Column(name = "rate_star")
    Integer rateStar;

    @Column(name = "rate_context", columnDefinition = "TEXT")
    String rateContext;

    @Column(name = "rate_datetime", updatable = false)
    private LocalDate rateDatetime;

    @PrePersist
    protected void onCreate() {
        this.rateDatetime = LocalDate.now();
    }
}
