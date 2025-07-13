package com.Hyperfume.Backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.Hyperfume.Backend.enums.ShipmentStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentDetailResponse {
    Integer id;
    Integer orderId;
    String shippingCode;
    String name;
    String description;
    BigDecimal fee;
    LocalDate expectedDeliveryDate;
    LocalDate actualDeliveryDate;
    ShipmentStatus status;
    String serviceId;
    String serviceName;
    String toAddress;
    String fromAddress;

    // Information about the shipment's current location or latest status update
    String currentLocation;
    String statusDescription;
    LocalDate lastUpdated;
}
