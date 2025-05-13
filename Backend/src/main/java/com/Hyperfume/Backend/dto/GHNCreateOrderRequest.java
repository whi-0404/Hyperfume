package com.Hyperfume.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GHNCreateOrderRequest {
    int orderId;

    int shippingAddressId;

    Integer serviceTypeId;
    Integer paymentTypeId;
    String requiredNote;

    int weight;
    int length;
    int width;
    int height;
}
