package com.Hyperfume.Backend.controller;

import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.UserCreationRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.dto.response.UserResponse;
import com.Hyperfume.Backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Integer userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable("userId") Integer userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        userService.deactivateUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deactivated successfully.")
                .build();
    }

    @PostMapping("/{userId}/favorites/{perfumeId}")
    ApiResponse<String> addPerfumeToFavorites(
            @PathVariable("userId") Integer userId, @PathVariable("perfumeId") Integer perfumeId) {
        userService.addPerfumeToFavorites(userId, perfumeId);

        return ApiResponse.<String>builder().result("Successfully").build();
    }

    @GetMapping("/{userId}/favorites")
    ApiResponse<Set<PerfumeResponse>> getFavoritesPerfumes(@PathVariable("userId") Integer userId) {
        return ApiResponse.<Set<PerfumeResponse>>builder()
                .result(userService.getFavoritePerfumes(userId))
                .build();
    }
}
