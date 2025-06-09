package com.bteamcoding.bubble_translation_be.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    private String password;
}
