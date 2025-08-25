package com.jamesmcdonald.backend.account;

public record RecipientDTO(
        Long id,
        String name,
        String email,
        String cardNumberLast4Digits
) {
}
