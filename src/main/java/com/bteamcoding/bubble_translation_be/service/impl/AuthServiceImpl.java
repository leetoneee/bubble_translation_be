package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.SignInRequest;
import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.AuthResponse;
import com.bteamcoding.bubble_translation_be.entity.User;
import com.bteamcoding.bubble_translation_be.excception.ResourceAlreadyExistsException;
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
    public AuthResponse signUp(SignUpRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new ResourceAlreadyExistsException("Email đã tồn tại");
        }
        User user = mapper.toEntity(req);
        user.setPassword(encoder.encode(req.getPassword()));
        userRepo.save(user);
        return new AuthResponse("Đăng ký thành công");
    }

    @Override
    public AuthResponse signIn(SignInRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        return new AuthResponse("Đăng nhâp thành công");
    }
}
