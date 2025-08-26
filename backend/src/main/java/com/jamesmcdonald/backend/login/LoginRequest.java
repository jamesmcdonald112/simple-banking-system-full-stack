package com.jamesmcdonald.backend.login;

import jakarta.validation.constraints.*;

/**
 * Payload for login requests containing user credentials.
 */
public record LoginRequest(
        /**
         * The card number used for login.
         * Must be exactly 16 digits.
         */
        @NotBlank
        @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
        String cardNumber,

        /**
         * The PIN associated with the card.
         * Must be exactly 4 digits.
         */
        @NotBlank
        @Pattern(regexp = "\\d{4}", message = "PIN must be 4 digits")
        String pin,

        /**
         * The password for the account.
         * Must be at least 4 characters long.
         */
        @NotBlank
        @Size(min = 4, message = "Password must be at least 4 characters")
        String password
) {}
