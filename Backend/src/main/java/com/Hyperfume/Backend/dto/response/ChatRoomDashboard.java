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
public class ChatRoomDashboard {
    int id;
    boolean isOnline;
    String username;
    int unreadCount;
    LocalDateTime lastActivity;
    MessageType typeRecentMessage;
}
