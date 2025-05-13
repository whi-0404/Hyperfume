package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.response.NotificationResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.entity.Notification;
import com.Hyperfume.Backend.enums.NotificationType;

import java.util.Map;

public interface NotificationService {
    void sendNotification(Notification notification);

    void markAsRead(Integer notificationId);

    void markAllAsRead();

    int countUnreadNotifications();

    PageResponse<NotificationResponse> getNotifications(int page, int size);

    PageResponse<NotificationResponse> getNotificationsByType(NotificationType type, int page, int size);
}
