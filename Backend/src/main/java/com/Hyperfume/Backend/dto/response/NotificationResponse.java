package com.Hyperfume.Backend.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    Integer id;

    String title;

    String content;

    String type;

    Boolean isRead;

    LocalDateTime createdAt;
}
