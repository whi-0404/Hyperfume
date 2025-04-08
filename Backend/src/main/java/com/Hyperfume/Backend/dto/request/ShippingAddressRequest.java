package com.Hyperfume.Backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingAddressRequest {
    String recipientName;

    String phone;

    String shipAddress;

    Boolean isDefault;
}
