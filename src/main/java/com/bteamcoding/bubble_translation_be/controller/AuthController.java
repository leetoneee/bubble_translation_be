package com.bteamcoding.bubble_translation_be.controller;

import com.bteamcoding.bubble_translation_be.dto.request.SignInRequest;
import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.ApiResponse;
import com.bteamcoding.bubble_translation_be.dto.response.AuthResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponseWrapper;
import com.bteamcoding.bubble_translation_be.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<UserResponseWrapper> signUp(@Valid @RequestBody SignUpRequest req) {
        return ApiResponse.<UserResponseWrapper>builder()
                .result(authService.signUp(req))
                .build();
    }

    @PostMapping("/signin")
    public ApiResponse<AuthResponse> signIn(@Valid @RequestBody SignInRequest req) {
        return ApiResponse.<AuthResponse>builder()
                .result(authService.signIn(req))
                .build();
    }
}
