package com.Hyperfume.Backend.entity;

import com.Hyperfume.Backend.enums.NotificationType;
import com.Hyperfume.Backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
