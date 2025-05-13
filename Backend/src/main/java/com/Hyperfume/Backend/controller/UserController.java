package com.Hyperfume.Backend.controller;

import java.util.List;
import java.util.Set;

import com.Hyperfume.Backend.dto.response.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.UserCreationRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
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

    @PostMapping("/favorites/{perfumeId}")
    ApiResponse<String> addPerfumeToFavorites(
            @PathVariable("perfumeId") Integer perfumeId) {
        userService.addPerfumeToFavorites(perfumeId);

        return ApiResponse.<String>builder().result("Successfully").build();
    }

    @DeleteMapping("/favorites/{perfumeId}")
    ApiResponse<String> removePerfumeFromFavorites(
            @PathVariable("perfumeId") Integer perfumeId) {
        userService.removePerfumeFromFavorites(perfumeId);

        return ApiResponse.<String>builder().result("Successfully").build();
    }

    @GetMapping("/favorites")
    ApiResponse<PageResponse<PerfumeGetAllResponse>> getFavoritesPerfumes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(userService.getFavoritePerfumes(page, size))
                .build();
    }

    @GetMapping("/favorites/checkFavorite")
    ApiResponse<String> checkFavoritePerfume(@RequestParam(value = "perfumeId") Integer perfumeId){
        boolean isFavoritePerfume = userService.isPerfumeInFavorites(perfumeId);

        return ApiResponse.<String>builder()
                .result(isFavoritePerfume ? "is Favorite Perfume" : "not Favorite Perfume")
                .build();
    }
}
