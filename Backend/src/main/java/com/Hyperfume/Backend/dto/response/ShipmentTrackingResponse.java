package com.Hyperfume.Backend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.Hyperfume.Backend.enums.ShipmentStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentTrackingResponse {
    String shippingCode;
    Integer orderId;
    ShipmentStatus currentStatus;
    String serviceName;
    LocalDate expectedDeliveryDate;
    String fromAddress;
    String toAddress;

    // Time-ordered list of status updates
    List<TrackingEvent> trackingHistory;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrackingEvent {
        LocalDateTime timestamp;
        ShipmentStatus status;
        String location;
        String description;
    }
}
