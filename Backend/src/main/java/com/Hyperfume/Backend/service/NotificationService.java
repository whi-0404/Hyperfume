package com.Hyperfume.Backend.service;

import java.util.Map;

public interface NotificationService {
    void sendOrderStatusNotification(Integer userId, String title, String content);
    void sendSystemNotification(Integer userId, String title, String content);
    void sendPromotionNotification(Integer userId, String title, String content);
}
