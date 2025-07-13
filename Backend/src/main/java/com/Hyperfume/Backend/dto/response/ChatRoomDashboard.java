package com.Hyperfume.Backend.dto.response;

import java.time.LocalDateTime;

import com.Hyperfume.Backend.enums.MessageType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomDashboard {
    int id;
    boolean isOnline;
    String username;
    int unreadCount;
    LocalDateTime lastActivity;
    MessageType typeRecentMessage;
}
