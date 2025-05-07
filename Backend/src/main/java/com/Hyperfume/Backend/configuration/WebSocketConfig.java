package com.Hyperfume.Backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefix cho các endpoint mà client sẽ subscribe để nhận thông báo
        registry.enableSimpleBroker("/topic");

        // Prefix cho các endpoint mà client sẽ gửi tin nhắn
        registry.setApplicationDestinationPrefixes("/app");

        // Prefix cho các tin nhắn cá nhân
        registry.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint để client kết nối WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Hỗ trợ SockJS cho browsers không hỗ trợ WebSocket
    }
}
