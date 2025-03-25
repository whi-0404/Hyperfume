package com.Hyperfume.Backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "perfume_variants")
public class PerfumeVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    int id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    Perfume perfume;

    BigDecimal price;

    int perfume_stock_quantity;
}
