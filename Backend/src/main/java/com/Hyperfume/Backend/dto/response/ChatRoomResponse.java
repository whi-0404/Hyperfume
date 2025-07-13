package com.Hyperfume.Backend.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomResponse {
    int id;
    int isOnline;
    List<ChatMessageResponse> recentMessages;
}
