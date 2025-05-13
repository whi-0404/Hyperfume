package com.Hyperfume.Backend.service.redis;

import com.Hyperfume.Backend.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatRedisService {

    RedisTemplate<String, Object> redisTemplate;

    private static final String ONLINE_USERS_KEY = "chat:online-users";
    private static final String UNREAD_COUNT_KEY = "chat:unread-count:";
    private static final long ONLINE_EXPIRATION = 300; // 5 minutes

    public void markUserOnline(int userId){
        String userKey = String.valueOf(userId);

        redisTemplate.opsForZSet().add(ONLINE_USERS_KEY, userKey, System.currentTimeMillis());
        redisTemplate.expire(ONLINE_USERS_KEY, ONLINE_EXPIRATION, TimeUnit.SECONDS);
    }

    public void markUserOffline(int userId) {
        String userKey = String.valueOf(userId);
        redisTemplate.opsForZSet().remove(ONLINE_USERS_KEY, userKey);
    }

    public void userHeartbeat(int userId) {
        String userKey = String.valueOf(userId);

        redisTemplate.opsForZSet().add(ONLINE_USERS_KEY, userKey, System.currentTimeMillis());
        redisTemplate.expire(ONLINE_USERS_KEY, ONLINE_EXPIRATION, TimeUnit.SECONDS);
    }

    public boolean isUserOnline(int userId) {
        String userKey = String.valueOf(userId);
        return redisTemplate.opsForZSet().score(ONLINE_USERS_KEY, userKey) != null;
    }

    /**
     * Tăng số lượng tin nhắn chưa đọc cho người dùng
     */
    public void incrementUnreadCount(int userId, int senderId) {
        String key = UNREAD_COUNT_KEY + userId + ":" + senderId;
        redisTemplate.opsForValue().increment(key);
    }
    /**
     * Đặt lại số lượng tin nhắn chưa đọc khi người dùng mở cuộc trò chuyện
     */
    public void resetUnreadCount(int userId, int senderId) {
        String key = UNREAD_COUNT_KEY + userId + ":" + senderId;
        redisTemplate.delete(key);
    }
    /**
     * Lấy số lượng tin nhắn chưa đọc từ một người gửi cụ thể
     */
    public int getUnreadCount(int userId, int senderId) {
        String key = UNREAD_COUNT_KEY + userId + ":" + senderId;
        Object count = redisTemplate.opsForValue().get(key);

        return count != null ? Integer.parseInt(count.toString()) : 0;
    }
    /**
     * Lấy tất cả số lượng tin nhắn chưa đọc cho một người dùng
     */
    public Map<String, Integer> getAllUnreadCounts(int userId) {
        String pattern = UNREAD_COUNT_KEY + userId + ":*";
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys.isEmpty()) {
            return Map.of();
        }

        return keys.stream()
                .collect(Collectors.toMap(
                        key -> key.split(":")[2], // Trích xuất senderId từ key
                        key -> {
                            Object count = redisTemplate.opsForValue().get(key);
                            return count != null ? Integer.parseInt(count.toString()) : 0;
                        }
                ));
    }
}
