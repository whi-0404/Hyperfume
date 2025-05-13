package com.Hyperfume.Backend.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    int id;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    PerfumeVariant perfumeVariant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    Integer quantity;
}
