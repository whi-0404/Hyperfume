package com.Hyperfume.Backend.dto.request;

import java.util.List;

import jakarta.validation.Valid;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    @Valid
    OrderRequest orderRequest;

    @Valid
    List<OrderItemRequest> orderItemRequests;
}
