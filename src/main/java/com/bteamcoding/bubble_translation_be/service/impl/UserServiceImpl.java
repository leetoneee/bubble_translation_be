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
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(Long userId) {
        return mapper.toUserResponse(userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        mapper.updateUser(user, request);

        return mapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }
}
