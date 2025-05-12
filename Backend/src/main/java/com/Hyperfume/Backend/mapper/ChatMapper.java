package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.ChatMessageRequest;
import com.Hyperfume.Backend.dto.response.ChatMessageResponse;
import com.Hyperfume.Backend.dto.response.ChatRoomDashboard;
import com.Hyperfume.Backend.dto.response.ChatRoomResponse;
import com.Hyperfume.Backend.entity.ChatMessage;
import com.Hyperfume.Backend.entity.ChatRoom;
import com.Hyperfume.Backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "receiver", source = "receiver")
    @Mapping(target = "chatRoom", source = "chatRoom")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ChatMessage toChatMessage(ChatMessageRequest request, User sender, User receiver, ChatRoom chatRoom);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderName", source = "sender.username")
    @Mapping(target = "receiverId", source = "receiver.id")
    @Mapping(target = "receiverName", source = "receiver.username")
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom);

    default ChatRoomDashboard toChatRoomDashboard(ChatRoom chatRoom,
                                                  ChatMessage lastMessage,
                                                  int unreadCount,
                                                  boolean isOnline) {
        return ChatRoomDashboard.builder()
                .id(chatRoom.getId())
                .username(chatRoom.getUser().getUsername())
                .unreadCount(unreadCount)
                .typeRecentMessage(lastMessage != null ? lastMessage.getType() : null)
                .lastActivity(chatRoom.getLastActivity())
                .isOnline(isOnline)
                .build();
    }
}
