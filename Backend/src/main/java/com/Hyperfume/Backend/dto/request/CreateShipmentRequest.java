package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateShipmentRequest {

    @NotNull
    Integer orderId;

    @NotNull
    String description;

    @NotNull
    Integer serviceId;

    @NotNull
    String serviceName;
}
