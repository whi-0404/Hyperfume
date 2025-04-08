package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingAddressResponse {
    Integer id;

    String recipientName;

    String phone;

    String shipAddress;

    boolean isDefault;
}
