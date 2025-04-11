package com.Hyperfume.Backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleRequest {
    @NotEmpty(message = "Name of Flash Sale cannot be empty")
    String name;

    String description;

    @NotNull(message = "Start time cannot be null")
    LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    LocalDateTime endTime;

    @Positive(message = "Discount percentage must be positive")
    Double discountPercentage;

    List<FlashSaleItemRequest> items;
}
