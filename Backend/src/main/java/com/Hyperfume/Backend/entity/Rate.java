package com.Hyperfume.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    Perfume perfume;

    @Column(name = "rate_star")
    Integer rateStar;

    @Column(name = "rate_context", columnDefinition = "TEXT")
    String rateContext;

    @Column(name = "rate_datetime", updatable = false)
    private LocalDate rateDatetime;
    @PrePersist
    protected void onCreate(){
        this.rateDatetime = LocalDate.now();
    }
}
