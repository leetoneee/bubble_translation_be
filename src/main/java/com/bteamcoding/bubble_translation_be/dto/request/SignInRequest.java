package com.bteamcoding.bubble_translation_be.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @Email @NotBlank private String email;
    @NotBlank private String password;
}
