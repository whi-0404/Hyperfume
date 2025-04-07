package com.Hyperfume.Backend.service;

import java.util.List;
import java.util.Set;

import com.Hyperfume.Backend.dto.request.UserCreationRequest;
import com.Hyperfume.Backend.dto.request.UserUpdateRequest;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse getUser(Integer userId);

    UserResponse getMyInfo();

    UserResponse updateUser(Integer userId, UserUpdateRequest request);

    void deleteUser(Integer userId);

    void deactivateUser(Integer userId);

    void addPerfumeToFavorites(Integer perfumeId);

    void removePerfumeFromFavorites(Integer perfumeId);

    PageResponse<PerfumeGetAllResponse> getFavoritePerfumes(int page, int size);

    boolean isPerfumeInFavorites(Integer perfumeId);
}
