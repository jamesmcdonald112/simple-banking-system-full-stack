package com.jamesmcdonald.backend.login;

public record LoginRequest(
        String cardNumber,
        String pin,
        String password
) { }
