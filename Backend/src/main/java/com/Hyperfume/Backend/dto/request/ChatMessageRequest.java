package com.Hyperfume.Backend.dto.request;

import com.Hyperfume.Backend.enums.MessageType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageRequest {
    int receiverId;
    String content;
    MessageType type;
}
