package com.bteamcoding.bubble_translation_be.service;

import com.bteamcoding.bubble_translation_be.dto.request.UserCreationRequest;
import com.bteamcoding.bubble_translation_be.dto.request.UserUpdateRequest;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);
    List<UserResponse> getUsers();
    UserResponse getUser(Long userId);
    UserResponse updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
}
