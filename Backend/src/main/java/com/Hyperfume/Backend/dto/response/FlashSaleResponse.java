package com.Hyperfume.Backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleResponse {
    int id;
    String name;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    double discountPercentage;
    boolean active;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<FlashSaleItemResponse> flashSaleItemResponseList;
}
