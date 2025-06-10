package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.SignInRequest;
import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.AuthResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.entity.User;
import com.bteamcoding.bubble_translation_be.excception.AppException;
import com.bteamcoding.bubble_translation_be.excception.ErrorCode;
import com.bteamcoding.bubble_translation_be.mapper.UserMapper;
import com.bteamcoding.bubble_translation_be.repository.UserRepository;
import com.bteamcoding.bubble_translation_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse signUp(SignUpRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = mapper.toEntity(req);
        user.setPassword(encoder.encode(req.getPassword()));
        return mapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public AuthResponse signIn(SignInRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        AuthResponse authResponse = new AuthResponse();

        authResponse.setAuthenticated(true);
        authResponse.setUser(
                mapper.toUserResponse(userRepo.findById(user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)))
        );

        return authResponse;
    }
}
