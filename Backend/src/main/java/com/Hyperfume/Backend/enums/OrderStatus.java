package com.Hyperfume.Backend.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER_CREATED("Đơn hàng mới được tạo"),
    PAYMENT_PENDING("Đơn hàng đang chờ thanh toán"),
    PAYMENT_COMPLETED("Đã thanh toán thành công"),
    ORDER_PENDING("Chờ xác nhận đơn hàng"),
    PROCESSING("Đơn hàng đang được chuẩn bị"),
    IN_TRANSIT("Đang vận chuyển"),
    DELIVERING("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    COMPLETED("Đơn hàng hoàn thành"),
    ORDER_CANCELLED("Đơn hàng đã hủy"),
    RETURNING("Đang trả hàng"),
    RETURNED("Đã trả hàng"),
    REFUNDED("Đã hoàn tiền");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
