package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DepositRequestDTO(
        @DecimalMin(value ="0.01", message = "Amount must be greater than 0")
        @DecimalMax(value = "100000", message = "Amount too large. Maximum amount is 100,000")
        @Digits(integer = 9, fraction = 2, message = "Max 2 decimal places")
        BigDecimal amount
) {
}
