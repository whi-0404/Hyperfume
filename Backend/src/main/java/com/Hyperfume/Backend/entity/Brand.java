package com.Hyperfume.Backend.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    int id;

    @Column(name = "brand_name", nullable = false, unique = true)
    String name;

    @OneToMany
    @JoinColumn(name = "brand_id")
    List<Perfume> perfumes;
}
