package com.Hyperfume.Backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.Hyperfume.Backend.enums.NotificationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    String title;

    @JoinColumn(columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    NotificationType type;

    @JoinColumn(name = "is_read")
    boolean isRead;

    //    @JoinColumn(name = "redirect_url")
    //    String redirectUrl;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
}
