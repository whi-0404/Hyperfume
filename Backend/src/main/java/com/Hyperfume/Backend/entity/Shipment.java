package com.Hyperfume.Backend.entity;

import com.Hyperfume.Backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ship_id")
    int id;

    @Column(name = "shipping_code")
    String shippingCode;

    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    Integer fee;

    @Column(name = "expected_delivery_date")
    LocalDate expectedDeliveryDate;

    @Column(name = "actual_delivery_date")
    LocalDate actualDeliveryDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ShipmentStatus status;

    @Column(name = "service_id")
    int serviceId;

    @Column(name = "current_location", columnDefinition = "TEXT")
    String currentLocation;
    
    @Column(name = "last_updated")
    LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    ShippingAddress shippingAddress;

    @OneToOne(mappedBy = "shipment")
    Order order;
    
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    List<ShipmentTracking> trackingHistory;
    
    @PrePersist
    protected void onCreate() {
        this.status = ShipmentStatus.SHIPMENT_PENDING;
    }

}
