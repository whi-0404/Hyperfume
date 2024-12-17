package com.Hyperfume.Backend.dto.request;

import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CreateOrderRequest {
    @Valid
    private OrderRequest orderRequest;

    @Valid
    private List<OrderItemRequest> orderItemRequests;
}
