package com.Hyperfume.Backend.dto.request;

import com.Hyperfume.Backend.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ShippingAddressRequest {
    String recipientName;

    String phone;

    String shipAddress;
}
