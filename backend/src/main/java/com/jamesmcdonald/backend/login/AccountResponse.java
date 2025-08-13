package com.jamesmcdonald.backend.login;

public record AccountResponse(
         Long id,
         String cardNumber,
         int balance,
         String name,
         String email,
         String phone
) {
}
