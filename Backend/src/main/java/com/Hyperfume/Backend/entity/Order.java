package com.Hyperfume.Backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.Hyperfume.Backend.enums.OrderStatus;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

//    @ManyToOne
//    @JoinColumn(name = "shipping_address_id")
//    ShippingAddress shippingAddress;

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;

    @Column(name = "order_date", updatable = false)
    LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @JoinColumn(name = "total_money")
    BigDecimal totalMoney;

//    @ManyToOne
//    @JoinColumn(name = "shipping_method_id")
//    ShippingMethod shippingMethod;

//    @JoinColumn(name = "shipping_date")
//    LocalDate shippingDate;

    @Column(name = "payment_transaction_id")
    String paymentTransactionId;

    @Column(name = "payment_transaction_time")
    LocalDate paymentTransactionTime;

    @ManyToOne
    @JoinColumn(name = "pay_id")
    PaymentMethod paymentMethod;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    List<OrderItem> orderItemList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id")
    Shipment shipment;

    boolean active;

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDate.now();
        this.active = true;
    }
}
