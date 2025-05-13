package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.request.ChatMessageRequest;
import com.Hyperfume.Backend.dto.response.ChatMessageResponse;
import com.Hyperfume.Backend.dto.response.ChatRoomDashboard;
import com.Hyperfume.Backend.dto.response.ChatRoomResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.entity.ChatMessage;
import com.Hyperfume.Backend.entity.ChatRoom;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.enums.MessageType;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ChatMapper;
import com.Hyperfume.Backend.repository.ChatMessageRepository;
import com.Hyperfume.Backend.repository.ChatRoomRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.ChatService;
import com.Hyperfume.Backend.service.FileService;
import com.Hyperfume.Backend.service.redis.ChatRedisService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatServiceImpl implements ChatService {
    ChatMessageRepository chatMessageRepository;
    ChatRoomRepository chatRoomRepository;
    UserRepository userRepository;
    ChatMapper chatMapper;
    ChatRedisService chatRedisService;
    SimpMessagingTemplate messagingTemplate;
    FileService fileService;

    @Transactional
    public ChatMessageResponse sendMessage(ChatMessageRequest request){
        User sender = getCurrentUser();

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //get or create chatRoom
        ChatRoom chatRoom = getChatRoomBetweenUserAndAdmin(sender, receiver);

        //update chatRoom
        chatRoom.setLastActivity(LocalDateTime.now());
        chatRoom.setActive(true);
        chatRoomRepository.save(chatRoom);

        //create message
        ChatMessage message = chatMapper.toChatMessage(request, sender, receiver, chatRoom);
        chatMessageRepository.save(message);

        // update unread message count in Redis
        chatRedisService.incrementUnreadCount(receiver.getId(), sender.getId());

        ChatMessageResponse response = chatMapper.toChatMessageResponse(message);

        // send notification to receiver
        messagingTemplate.convertAndSendToUser(
                String.valueOf(receiver.getId()),
                "/queue/messages",
                response
        );

        return response;
    }


    public ChatMessageResponse sendMediaMessage(int receiverId, String content, String type, MultipartFile file){
        User sender = getCurrentUser();

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String fileUrl = fileService.uploadFile(file, "chatMedia", type.toLowerCase());

        ChatRoom chatRoom = getChatRoomBetweenUserAndAdmin(sender, receiver);

        chatRoom.setLastActivity(LocalDateTime.now());
        chatRoom.setActive(true);
        chatRoomRepository.save(chatRoom);

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content != null && !content.isEmpty() ? content + "|" + fileUrl : fileUrl)
                .type(MessageType.valueOf(type.toUpperCase()))
                .chatRoom(chatRoom)
                .build();

        chatMessageRepository.save(message);

        chatRedisService.incrementUnreadCount(receiver.getId(), sender.getId());

        ChatMessageResponse response = chatMapper.toChatMessageResponse(message);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(receiver.getId()),
                "/queue/messages",
                response
        );

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ChatMessageResponse> getChatHistory(int chatRoomId, int page, int size){
        User currentUser = getCurrentUser();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new AppException(ErrorCode.CHATROOM_NOT_EXISTED));

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ChatMessage> messagePage = chatMessageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageable);

        if (currentUser.getId() == chatRoom.getAdmin().getId()) {
            chatMessageRepository.markMessagesAsReadByReceiverAndSender(
                    currentUser.getId(), chatRoom.getUser().getId());

            chatRedisService.resetUnreadCount(currentUser.getId(), chatRoom.getUser().getId());
        } else {
            chatMessageRepository.markMessagesAsReadByReceiverAndSender(
                    currentUser.getId(), chatRoom.getAdmin().getId());

            chatRedisService.resetUnreadCount(currentUser.getId(), chatRoom.getAdmin().getId());
        }

        List<ChatMessageResponse> messages = messagePage.getContent().stream()
                .map(chatMapper::toChatMessageResponse)
                .collect(Collectors.toList());

        return PageResponse .<ChatMessageResponse>builder()

                .totalPages(messagePage.getTotalPages())
                .pageSize(messagePage.getSize())
                .totalElements(messagePage.getTotalElements())
                .Data(messages)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ChatRoomDashboard> getAdminChatRooms(){
        User admin = getCurrentUser();

        List<ChatRoom> chatRooms = chatRoomRepository.findAllChatRoomsForAdmin(admin);

        return chatRooms.stream()
                .map(chatRoom -> {
                    ChatMessage lastMessage = chatMessageRepository.findTopByChatRoomOrderByCreatedAtDesc(chatRoom)
                            .orElse(null);

                    int unreadCount = chatRedisService.getUnreadCount(admin.getId(), chatRoom.getUser().getId());

                    boolean isOnline = chatRedisService.isUserOnline(chatRoom.getUser().getId());

                    return chatMapper.toChatRoomDashboard(chatRoom, lastMessage, unreadCount, isOnline);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void markMessagesAsRead(int chatRoomId){
        User currentUser = getCurrentUser();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()-> new AppException(ErrorCode.CHATROOM_NOT_EXISTED));

        if(currentUser.getId() == chatRoom.getAdmin().getId()){
            chatMessageRepository.markMessagesAsReadByReceiverAndSender(
                    currentUser.getId(), chatRoom.getUser().getId());

            chatRedisService.resetUnreadCount(currentUser.getId(), chatRoom.getUser().getId());
        }
        else {
            chatMessageRepository.markMessagesAsReadByReceiverAndSender(
                    currentUser.getId(), chatRoom.getAdmin().getId());

            chatRedisService.resetUnreadCount(currentUser.getId(), chatRoom.getAdmin().getId());
        }
    }

    public int getTotalUnreadMessages(){
        User currentUser = getCurrentUser();

        Map<String, Integer> unreadCounts = chatRedisService.getAllUnreadCounts(currentUser.getId());

        return unreadCounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getUnreadMessagesFromUser(int userId){
        User currentUser = getCurrentUser();
        return chatRedisService.getUnreadCount(currentUser.getId(), userId);
    }

    private User getCurrentUser(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
    }

    private ChatRoom getChatRoomBetweenUserAndAdmin(User user1, User user2){
        User user;
        User admin;

        if (user1.getRole().getId() == 1) {
            admin = user1;
            user = user2;
        } else if (user2.getRole().getId() == 1) {
            admin = user2;
            user = user1;
        } else {
            throw new AppException(ErrorCode.INVALID_ROLE_CHAT);
        }
        return chatRoomRepository.findByUserAndAdmin(user, admin)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = ChatRoom.builder()
                            .user(user)
                            .admin(admin)
                            .lastActivity(LocalDateTime.now())
                            .active(true)
                            .build();
                    return chatRoomRepository.save(newChatRoom);
                });
    }
}
