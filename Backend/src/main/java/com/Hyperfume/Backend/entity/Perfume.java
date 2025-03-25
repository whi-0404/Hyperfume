package com.Hyperfume.Backend.entity;

import java.time.LocalDate;
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
@Table(name = "perfumes")
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    int id;

    @Column(name = "perfume_name")
    String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    String type;

    @Column(name = "perfume_description", columnDefinition = "TEXT")
    String perfume_description;

    String perfume_gender;
    String concentration;

    @ManyToOne
    @JoinColumn(name = "scrent_family_id")
    ScrentFamily screntFamily;

    String main_notes;
    String longevity;
    String sillage;
    String style;
    String season_usage;
    int release_year;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    String top_notes;
    String middle_notes;
    String base_notes;

    @Column(name = "is_sale", nullable = false)
    boolean sale;

    @Column(name = "is_flash_sale")
    boolean flash_sale;

    int sold;

    @Column(name = "discount", columnDefinition = "DOUBLE DEFAULT 0.0")
    double discount;

    @Column(name = "created_at", updatable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfume_id")
    List<PerfumeImage> images;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfume_id")
    List<PerfumeVariant> variants;
}
