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
@Table(name = "order_items")
public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    PerfumeVariant perfumeVariant;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "quantity")
    int quantity;
}
