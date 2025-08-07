package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AccountRequestDTO(
        @NotBlank  String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^\\+?[0-9\\s]{7,15}$")String phone,
        @NotBlank String password) {
}
