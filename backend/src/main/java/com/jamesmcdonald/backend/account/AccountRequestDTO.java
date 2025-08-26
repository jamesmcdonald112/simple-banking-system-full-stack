package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for creating an account.
 */
public record AccountRequestDTO(
        /**
         * The name of the account holder. Must not be blank.
         */
        @NotBlank  String name,
        /**
         * The email address of the account holder. Must not be blank and must be a valid email format.
         */
        @NotBlank @Email String email,
        /**
         * The phone number of the account holder. Must not be blank and must match the pattern allowing optional '+' and 7 to 15 digits/spaces.
         */
        @NotBlank @Pattern(regexp = "^\\+?[0-9\\s]{7,15}$")String phone,
        /**
         * The password for the account. Must not be blank.
         */
        @NotBlank String password) {
}
