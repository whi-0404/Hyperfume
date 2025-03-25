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
    private OrderRequest orderRequest;

    @Valid
    private List<OrderItemRequest> orderItemRequests;
}
