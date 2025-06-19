package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.UserCreationRequest;
import com.bteamcoding.bubble_translation_be.dto.request.UserUpdateRequest;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.entity.User;
import com.bteamcoding.bubble_translation_be.excception.AppException;
import com.bteamcoding.bubble_translation_be.excception.ErrorCode;
import com.bteamcoding.bubble_translation_be.mapper.UserMapper;
import com.bteamcoding.bubble_translation_be.repository.UserRepository;
import com.bteamcoding.bubble_translation_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = mapper.toUser(request);
        user.setPassword(encoder.encode(user.getPassword()));
        return mapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepo.findAllActiveUsers();
        return users.stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(Long userId) {
        return mapper.toUserResponse(userRepo.findActiveById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepo.findActiveById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        mapper.updateUser(user, request);

        return mapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public UserResponse updateUserPartial(Long id, UserUpdateRequest req) {
        User user = userRepo.findActiveById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }

        if (req.getEmail() != null) {
            // Check email đã tồn tại và không phải của user hiện tại
            userRepo.findByEmail(req.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(user.getId()) && existing.getDeletedAt() == null) {
                    throw new AppException(ErrorCode.USER_EXISTED);
                }
            });
            user.setEmail(req.getEmail());
        }

        if (req.getPassword() != null) {
            user.setPassword(encoder.encode(req.getPassword()));
        }

        return mapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public void softDeleteUser(Long userId) {
        User user = userRepo.findActiveById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setDeletedAt(LocalDateTime.now());
        userRepo.save(user);
    }
}
