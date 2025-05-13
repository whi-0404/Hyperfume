package com.Hyperfume.Backend.entity;

import com.Hyperfume.Backend.enums.OrderStatus;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "shipment_tracking")
public class ShipmentTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ship_id")
    Shipment shipment;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ShipmentStatus status;
    
    @Column(name = "location")
    String location;
    
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    
    @Column(name = "tracking_time")
    LocalDateTime trackingTime;

    @Column(name = "is_active")
    boolean active;
    
    @PrePersist
    protected void onCreate() {
        this.trackingTime = LocalDateTime.now();
        this.active = true;
    }
} 