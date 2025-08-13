package com.jamesmcdonald.backend.login;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank
        @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
        String cardNumber,

        @NotBlank
        @Pattern(regexp = "\\d{4}", message = "PIN must be 4 digits")
        String pin,

        @NotBlank
        @Size(min = 4, message = "Password must be at least 4 characters")
        String password
) {}
