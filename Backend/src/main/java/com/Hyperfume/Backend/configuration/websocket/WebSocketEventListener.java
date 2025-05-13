package com.Hyperfume.Backend.configuration.websocket;

import com.Hyperfume.Backend.service.redis.ChatRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    private final ChatRedisService chatRedisService;

    public WebSocketEventListener(ChatRedisService chatRedisService) {
        this.chatRedisService = chatRedisService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Authentication auth = (Authentication) event.getUser();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            try {
                // Lấy JWT từ authentication object
                Jwt jwt = jwtAuth.getToken();

                // Lấy thông tin người dùng từ JWT
                Integer userId = jwt.getClaim("userId");

                if (userId != null) {
                    // Đánh dấu người dùng online trong Redis
                    chatRedisService.markUserOnline(userId);
                    log.debug("User {} đã kết nối WebSocket", userId);
                }
            } catch (Exception e) {
                log.error("Lỗi xử lý kết nối WebSocket: {}", e.getMessage());
            }
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Authentication auth = (Authentication) event.getUser();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            try {
                // Lấy JWT từ authentication object
                Jwt jwt = jwtAuth.getToken();

                // Lấy thông tin người dùng từ JWT
                Integer userId = jwt.getClaim("userId");

                if (userId != null) {
                    // Đánh dấu người dùng offline trong Redis
                    chatRedisService.markUserOffline(userId);
                    log.debug("User {} đã ngắt kết nối WebSocket", userId);
                }
            } catch (Exception e) {
                log.error("Lỗi xử lý ngắt kết nối WebSocket: {}", e.getMessage());
            }
        }
    }
}
