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
@Table(name = "shipping_methods")
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ship_id")
    int id;

    @Column(name = "ship_name", nullable = false, unique = true)
    String name;

    @Column(name = "ship_cost")
    BigDecimal shipCost;
}
