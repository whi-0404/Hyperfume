package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hyperfume.Backend.dto.request.UserCreationRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.dto.response.UserResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.Role;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeMapper;
import com.Hyperfume.Backend.mapper.UserMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.RoleRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PerfumeMapper perfumeMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    PerfumeRepository perfumeRepository;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userrole =
                roleRepository.findByName("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        user.setRole(userrole);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {

        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(Integer userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(Integer userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deactivateUser(Integer userId) {
        userRepository.deactivateUser(userId);
    }

    @Transactional
    public void addPerfumeToFavorites(Integer userId, Integer perfumeId) {
        User user = userRepository
                .findByIdWithFavorites(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Perfume perfume = perfumeRepository
                .findById(perfumeId)
                .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        if (user.getFavoritePerfumes().contains(perfume)) {
            throw new AppException(ErrorCode.PERFUME_ALREADY_IN_FAVORITES);
        }

        user.getFavoritePerfumes().add(perfume);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<PerfumeResponse> getFavoritePerfumes(Integer userId) {
        User user = userRepository
                .findByIdWithFavorites(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Set<Perfume> perfumes = user.getFavoritePerfumes();

        return perfumes.stream().map(perfumeMapper::toResponse).collect(Collectors.toSet());
    }
}
