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
@Table(name="perfume_image")
public class PerfumeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    int id;

    @ManyToOne
    @JoinColumn(name = "perfume_id")
    Perfume perfume;

    @Column(name = "is_thumbnail",  nullable = false)
    boolean thumbnail;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    byte[] image_data;
}
