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
@Table(name = "flash_sale_items")
public class FlashSaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "flash_sale_id", nullable = false)
    FlashSale flashSale;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    Perfume perfume;

    @Column(name = "quantity_limit", nullable = false)
    int quantityLimit;

    @Column(name = "quantity_sold", nullable = false)
    int quantitySold;

    @Column(name = "special_discount_percentage")
    Double specialDiscountPercentage;

    @Column(name = "is_active")
    boolean isActive;
}
