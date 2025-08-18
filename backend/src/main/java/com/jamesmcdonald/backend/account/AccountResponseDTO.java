package com.jamesmcdonald.backend.account;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String cardNumber,
        String pin,
        String name,
        String email,
        String phone,
        BigDecimal balance
) {}
