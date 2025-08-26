package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for deposit requests.
 *
 * @param amount the deposit amount; must be greater than 0, less than or equal to 100,000,
 *               and can have a maximum of 2 decimal places.
 */
public record DepositRequestDTO(
        @DecimalMin(value ="0.01", message = "Amount must be greater than 0")
        @DecimalMax(value = "100000", message = "Amount too large. Maximum amount is 100,000")
        @Digits(integer = 9, fraction = 2, message = "Max 2 decimal places")
        BigDecimal amount
) {
}
