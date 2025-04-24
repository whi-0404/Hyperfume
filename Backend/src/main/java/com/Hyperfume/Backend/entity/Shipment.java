package com.Hyperfume.Backend.entity;

import com.Hyperfume.Backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    BigDecimal fee;

    @Column(name = "expected_delivery_date")
    LocalDate expectedDeliveryDate;

    @Column(name = "actual_delivery_date")
    LocalDate actualDeliveryDate;

    @Column(name = "status")
    ShipmentStatus status;

    @ManyToOne
    @JoinColumn(name = "shipping_method_id")
    ShippingAddress shippingAddress;

    @OneToOne(mappedBy = "shipment")
    Order oder;
}
