package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
