package com.Hyperfume.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="perfume_variants")
public class PerfumeVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    int id;

    @Column(name = "name",nullable = false, unique = true)
    String name;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    Perfume perfume;

    BigDecimal price;

    @Column(name = "discount", columnDefinition = "DOUBLE DEFAULT 0.0")
    double discount;

    int perfume_stock_quantity;
}
