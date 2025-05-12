package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.ChatMessage;
import com.Hyperfume.Backend.entity.ChatRoom;
import com.Hyperfume.Backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT m FROM ChatMessage m " +
            "WHERE (m.sender = :user1 AND m.receiver = :user2)" +
            "OR (m.sender = :user2 AND m.receiver = :user1)" +
            "ORDER BY m.createdAt DESC")
    Page<ChatMessage> findChatHistory(User user1, User user2, Pageable pageable);

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.sender = :sender " +
            "AND m.receiver = :receiver AND m.isRead = false " +
            "ORDER BY m.createdAt DESC")
    List<ChatMessage> findBySenderAndReceiverAndReadFalseOrderByCreatedAtDesc(User sender, User receiver);

    Page<ChatMessage> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);

    Optional<ChatMessage> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    @Modifying
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.receiver.id = :receiverId AND m.sender.id = :senderId AND m.isRead = false")
    void markMessagesAsReadByReceiverAndSender(@Param("receiverId") int receiverId, @Param("senderId") int senderId);

    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.receiver.id = :userId AND m.isRead = false")
    int countUnreadMessagesByReceiverId(@Param("userId") int userId);

    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.receiver.id = :receiverId AND m.sender.id = :senderId AND m.isRead = false")
    int countUnreadMessagesByReceiverAndSender(@Param("receiverId") int receiverId, @Param("senderId") int senderId);
}
