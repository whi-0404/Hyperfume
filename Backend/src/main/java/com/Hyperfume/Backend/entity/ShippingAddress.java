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
@Table(name = "shipping_address")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "recipient_name", nullable = false)
    String recipientName;

    @Column(name = "phone", nullable = false)
    String phone;

    @Column(name = "ship_address", nullable = false, columnDefinition = "TEXT")
    String shipAddress;

    @Column(name = "is_default")
    Boolean isDefault;
}
