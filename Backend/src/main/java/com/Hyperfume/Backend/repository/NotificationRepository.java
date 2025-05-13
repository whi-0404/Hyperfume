package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.Notification;
import com.Hyperfume.Backend.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);

    Page<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Integer userId, NotificationType type, Pageable pageable);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true " +
            "WHERE n.id = :notificationId AND n.user.id = :userId")
    void markAsRead(@Param("notificationId") Integer notificationId, @Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
    void markAllAsRead(@Param("userId") Integer userId);

    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    int countByUserIdAndReadIsFalse(@Param("userId") Integer userId);
}
