package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.ChatMessageRequest;
import com.Hyperfume.Backend.dto.response.ChatMessageResponse;
import com.Hyperfume.Backend.service.ChatService;
import com.Hyperfume.Backend.service.redis.ChatRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {
    private final ChatService chatService;
    private final ChatRedisService chatRedisService;

    @MessageMapping("/chat.heartbeat")
    public void processHeartbeat(SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal instanceof JwtAuthenticationToken jwtAuth) {
            try {
                Jwt jwt = jwtAuth.getToken();
                Integer userId = jwt.getClaim("userId");

                if (userId != null) {
                    chatRedisService.userHeartbeat(userId);
                }
            } catch (Exception e) {
                log.error("Lỗi xử lý heartbeat: {}", e.getMessage());
            }
        }
    }

    @MessageMapping("/chat.send")
    @SendToUser("/queue/messages")
    public ChatMessageResponse sendMessage(@Payload ChatMessageRequest request, Principal principal) {
        return chatService.sendMessage(request);
    }

    @MessageMapping("/chat.markRead")
    public void markMessagesAsRead(@Payload int chatRoomId) {
        chatService.markMessagesAsRead(chatRoomId);
    }
}
