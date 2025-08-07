package com.jamesmcdonald.backend.account;

public record AccountResponseDTO(
        Long id,
        String cardNumber,
        String name,
        String email,
        String phone,
        int balance
) {}
