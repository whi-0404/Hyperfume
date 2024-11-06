package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.UserCreationRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
import com.Hyperfume.Backend.dto.response.UserResponse;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.enums.Role;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.UserMapper;
import com.Hyperfume.Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request)
    {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user=userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = new HashSet<String>();
        roles.add(Role.USER.name());

        user.setRoles(roles);


        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers()
    {

        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(Integer userId)
    {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }


    public UserResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user=userRepository.findByUsername(name)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Integer userId, UserUpdateRequest request)
    {
       User user=userRepository.findById(userId)
               .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
       userMapper.updateUser(user, request);

       return  userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Integer userId)
    {
        userRepository.deleteById(userId);
    }
}
