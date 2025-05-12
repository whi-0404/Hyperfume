package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.ChatMessageRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ChatMessageResponse;
import com.Hyperfume.Backend.dto.response.ChatRoomDashboard;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatController {
    ChatService chatService;

    @PostMapping("/messages")
    public ApiResponse<ChatMessageResponse> sendMessage(@RequestBody ChatMessageRequest request){
        return ApiResponse.<ChatMessageResponse>builder()
                .result(chatService.sendMessage(request))
                .build();
    }

    @PostMapping(value = "/messages/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ChatMessageResponse> sendMediaMessage(
            @RequestParam("receiverId") int receiverId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file) {

        return ApiResponse.<ChatMessageResponse>builder()
                .result(chatService.sendMediaMessage(receiverId, content, type, file))
                .build();
    }

    @GetMapping("/rooms/admin")
    public ApiResponse<List<ChatRoomDashboard>> getAdminChatRooms() {
        return ApiResponse.<List<ChatRoomDashboard>>builder()
                .result(chatService.getAdminChatRooms())
                .build();
    }

    @GetMapping("/history/{chatRoomId}")
    public ApiResponse<PageResponse<ChatMessageResponse>> getChatHistory(
            @PathVariable int chatRoomId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return ApiResponse.<PageResponse<ChatMessageResponse>>builder()
                .result(chatService.getChatHistory(chatRoomId, page, size))
                .build();
    }

    @PostMapping("/read/{chatRoomId}")
    public ApiResponse<String> markMessagesAsRead(@PathVariable int chatRoomId) {
        chatService.markMessagesAsRead(chatRoomId);
        return ApiResponse.<String>builder()
                .result("successful!")
                .build();
    }

    @GetMapping("/unread/count")
    public ApiResponse<Integer> getTotalUnreadMessages() {
        return ApiResponse.<Integer>builder()
                .result(chatService.getTotalUnreadMessages())
                .build();
    }
}
