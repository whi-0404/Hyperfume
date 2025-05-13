package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.response.NotificationResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;
    SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification){
        notificationRepository.save(notification);

        //websocket
        NotificationResponse notificationResponse = convertToResponse(notification);

        try{
        messagingTemplate.convertAndSendToUser(
                notification.getUser().getUsername(),
                "/topic/notifications",
                notificationResponse
        );
        } catch (Exception e){
            log.error("Lỗi khi gửi thông báo WebSocket", e);
        }
    }

    public void markAsRead(Integer notificationId){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        notificationRepository.findById(notificationId)
                .orElseThrow(()-> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED));

        notificationRepository.markAsRead(notificationId, user.getId());
    }

    public void markAllAsRead(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        notificationRepository.markAllAsRead(user.getId());
    }

    public int countUnreadNotifications(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return notificationRepository.countByUserIdAndReadIsFalse(user.getId());
    }

    public PageResponse<NotificationResponse> getNotifications(int page, int size){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);

        return PageResponse.<NotificationResponse>builder()
                .pageSize(notifications.getSize())
                .totalPages(notifications.getTotalPages())
                .totalElements(notifications.getTotalElements())
                .Data(notifications.stream().map(this::convertToResponse).toList())
                .build();
    }

    public PageResponse<NotificationResponse> getNotificationsByType(NotificationType type, int page, int size){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(user.getId(), type, pageable);

        return PageResponse.<NotificationResponse>builder()
                .pageSize(notifications.getSize())
                .totalPages(notifications.getTotalPages())
                .totalElements(notifications.getTotalElements())
                .Data(notifications.stream().map(this::convertToResponse).toList())
                .build();
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
