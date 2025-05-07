package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.response.NotificationResponse;
import com.Hyperfume.Backend.entity.Notification;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.enums.NotificationType;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.NotificationRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;
    SimpMessagingTemplate messagingTemplate;

    public void sendOrderStatusNotification(Integer userId, String title, String content){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(NotificationType.ORDER_STATUS)
                .build();

        notificationRepository.save(notification);

        //websocket
        NotificationResponse notificationResponse = convertToResponse(notification);

        try{
        messagingTemplate.convertAndSendToUser(
                user.getUsername(),
                "/topic/notifications",
                notificationResponse
        );
        } catch (Exception e){
            log.error("Lỗi khi gửi thông báo WebSocket", e);
        }
    }
    public void sendSystemNotification(Integer userId, String title, String content){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(NotificationType.SYSTEM)
                .build();

        notificationRepository.save(notification);

        //websocket
    }
    public void sendPromotionNotification(Integer userId, String title, String content){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(NotificationType.PROMOTION)
                .build();

        notificationRepository.save(notification);

        //websocket


    }

    private NotificationResponse convertToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .type(notification.getType().toString())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
