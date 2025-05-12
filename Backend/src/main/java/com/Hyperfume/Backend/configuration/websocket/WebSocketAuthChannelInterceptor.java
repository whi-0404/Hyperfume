package com.Hyperfume.Backend.configuration.websocket;

import com.Hyperfume.Backend.configuration.security.CustomJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {
    private final CustomJwtDecoder jwtDecoder;

    public WebSocketAuthChannelInterceptor(CustomJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Xử lý xác thực từ cookie JWT
            String token = extractTokenFromCookie(accessor);

            if (token != null) {
                try {
                    // Xác thực JWT
                    Jwt jwt = jwtDecoder.decode(token);

                    // Lấy thông tin user từ JWT
                    String username = jwt.getSubject();
                    Integer userId = jwt.getClaim("userId");

                    // Tạo Authentication object và thiết lập trong context
                    Authentication auth = new JwtAuthenticationToken(jwt, null, username);

                    // Thiết lập Principal cho kết nối websocket
                    accessor.setUser(auth);

                } catch (Exception e) {
                    // Log lỗi xác thực nhưng không ném exception
                    // Kết nối vẫn được chấp nhận, nhưng các endpoint được bảo vệ sẽ từ chối
                }
            }
        }

        return message;
    }

    private String extractTokenFromCookie(StompHeaderAccessor accessor) {
        Map<String, List<String>> nativeHeaders = accessor.getMessageHeaders()
                .get(StompHeaderAccessor.NATIVE_HEADERS, Map.class);

        if (nativeHeaders != null && nativeHeaders.containsKey("Cookie")) {
            List<String> cookies = nativeHeaders.get("Cookie");

            if (cookies != null && !cookies.isEmpty()) {
                for (String cookie : cookies) {
                    // Tìm kiếm cookie "jwt=..."
                    String[] cookiePairs = cookie.split(";");
                    for (String pair : cookiePairs) {
                        String[] keyValue = pair.trim().split("=", 2);
                        if (keyValue.length == 2 && "jwt".equals(keyValue[0])) {
                            return keyValue[1];
                        }
                    }
                }
            }
        }

        // Nếu không tìm thấy trong cookie, thử tìm trong header token riêng
        if (nativeHeaders != null && nativeHeaders.containsKey("X-Auth-Token")) {
            List<String> tokens = nativeHeaders.get("X-Auth-Token");
            if (tokens != null && !tokens.isEmpty()) {
                return tokens.get(0);
            }
        }

        return null;
    }
}
