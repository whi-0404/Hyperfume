package com.Hyperfume.Backend.dto.response;

import com.Hyperfume.Backend.enums.MessageType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    int id;
    int senderId;
    String senderName;
    int receiverId;
    String receiverName;
    String content;
    MessageType type;
    LocalDateTime createdAt;
    boolean isRead;
}
