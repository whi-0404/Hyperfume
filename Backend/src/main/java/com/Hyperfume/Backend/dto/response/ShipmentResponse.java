package com.Hyperfume.Backend.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
