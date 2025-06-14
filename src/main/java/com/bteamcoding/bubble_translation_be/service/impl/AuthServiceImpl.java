package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.SignInRequest;
import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.AuthResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponseWrapper;
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
    public UserResponseWrapper signUp(SignUpRequest req) {
        userRepo.findByEmail(req.getEmail()).ifPresent(user -> {
            if (user.getDeletedAt() == null) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
        });

        User user = mapper.toEntity(req);
        user.setPassword(encoder.encode(req.getPassword()));

        UserResponseWrapper userResponseWrapper = new UserResponseWrapper();
        userResponseWrapper.setUser(mapper.toUserResponse(userRepo.save(user)));

        return userResponseWrapper;
    }

    @Override
    public AuthResponse signIn(SignInRequest req) {
        User user = userRepo.findActiveByEmail(req.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        AuthResponse authResponse = new AuthResponse();

        authResponse.setAuthenticated(true);
        authResponse.setUser(
                mapper.toUserResponse(user)
        );

        return authResponse;
    }
}
