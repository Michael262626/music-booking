package com.africa.musicbookingapp.music_booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Provide Email")
    private String email;
    @NotBlank(message = "Provide Password")
    private String password;
}
