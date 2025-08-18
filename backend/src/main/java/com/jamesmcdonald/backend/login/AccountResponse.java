package com.jamesmcdonald.backend.login;

import java.math.BigDecimal;

public record AccountResponse(
         Long id,
         String cardNumber,
         BigDecimal balance,
         String name,
         String email,
         String phone
) {
}
