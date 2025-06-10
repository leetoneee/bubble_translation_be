package com.bteamcoding.bubble_translation_be.service;

import com.bteamcoding.bubble_translation_be.dto.request.SignInRequest;
import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.AuthResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;

public interface AuthService {
    UserResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);
}
