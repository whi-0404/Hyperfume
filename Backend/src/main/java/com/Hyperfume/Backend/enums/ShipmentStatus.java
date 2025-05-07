package com.Hyperfume.Backend.enums;

import lombok.Getter;

@Getter
public enum ShipmentStatus {
    SHIPMENT_PENDING("Chờ xử lý vận chuyển"),
    READY_TO_PICK("Mới tạo đơn hàng"),
    PICKING("Nhân viên đang lấy hàng"),
    CANCEL("Hủy đơn hàng"),
    MONEY_COLLECT_PICKING("Đang thu tiền người gửi"),
    PICKED("Nhân viên đã lấy hàng"),
    STORING("Hàng đang nằm ở kho"),
    TRANSPORTING("Đang luân chuyển hàng"),
    SORTING("Đang phân loại hàng hóa"),
    DELIVERING("Nhân viên đang giao cho người nhận"),
    MONEY_COLLECT_DELIVERING("Nhân viên đang thu tiền người nhận"),
    DELIVERED("Nhân viên đã giao hàng thành công"),
    DELIVERY_FAIL("Nhân viên giao hàng thất bại"),
    WAITING_TO_RETURN("Đang đợi trả hàng về cho người gửi"),
    RETURN("Trả hàng"),
    RETURN_TRANSPORTING("Đang luân chuyển hàng trả"),
    RETURN_SORTING("Đang phân loại hàng trả"),
    RETURNING("Nhân viên đang đi trả hàng"),
    RETURN_FAIL("Nhân viên trả hàng thất bại"),
    RETURNED("Nhân viên trả hàng thành công"),
    EXCEPTION("Đơn hàng ngoại lệ không nằm trong quy trình"),
    DAMAGE("Hàng bị hư hỏng"),
    LOST("Hàng bị mất");

    private final String description;

    ShipmentStatus(String description) {
        this.description = description;
    }

}
