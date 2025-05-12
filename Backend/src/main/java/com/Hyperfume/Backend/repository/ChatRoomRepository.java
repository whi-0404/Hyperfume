package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.ChatRoom;
import com.Hyperfume.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    Optional<ChatRoom> findByUserAndAdmin(User user, User admin);

    @Query("SELECT c FROM ChatRoom c WHERE c.admin = :admin ORDER BY c.lastActivity DESC")
    List<ChatRoom> findAllChatRoomsForAdmin(User admin);

    Optional<ChatRoom> findByUserId(int userId);
}
