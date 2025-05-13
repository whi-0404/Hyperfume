package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentResponse {
    String shipmentToken;

    int fee;

    LocalDate expectedDeliveryDate;

    Integer serviceId;

    String serviceName;

    Integer shippingAddressId;

    String shippingAddress;
}
