package com.Hyperfume.Backend.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import com.Hyperfume.Backend.enums.ShipmentStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateShipmentStatusRequest {

    @NotNull
    Integer shipmentId;

    @NotNull
    ShipmentStatus status;

    // Optional fields
    String shippingCode; // Updated shipping code if available
    LocalDate actualDeliveryDate; // For delivered shipments
    String statusDescription; // Additional status information
}
