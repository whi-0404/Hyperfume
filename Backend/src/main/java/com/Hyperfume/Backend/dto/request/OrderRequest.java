package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotNull
    Integer shippingAddressId;

    String notes;

    @NotNull
    Integer shippingMethodId;

    @NotNull
    Integer paymentMethodId;
}
