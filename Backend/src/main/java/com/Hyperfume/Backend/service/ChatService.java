package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ChatMessageRequest;
import com.Hyperfume.Backend.dto.response.ChatMessageResponse;
import com.Hyperfume.Backend.dto.response.ChatRoomDashboard;
import com.Hyperfume.Backend.dto.response.ChatRoomResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChatService {
    ChatMessageResponse sendMessage(ChatMessageRequest request);

    PageResponse<ChatMessageResponse> getChatHistory(int chatRoomId, int page, int size);

    ChatMessageResponse sendMediaMessage(int receiverId, String content, String type, MultipartFile file);

    List<ChatRoomDashboard> getAdminChatRooms();

    void markMessagesAsRead(int chatRoomId);

    int getTotalUnreadMessages();

    /**
     * Get the count of unread messages for admin from specific user
     */
    int getUnreadMessagesFromUser(int userId);
}
