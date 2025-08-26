package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * DTO representing a request payload for transferring money between accounts.
 *
 * @param fromAccountId the unique identifier of the account to transfer funds from
 * @param toAccountId the unique identifier of the account to transfer funds to
 * @param amount the amount of money to transfer; must be positive
 */
public record TransferRequestDTO(
        @NotNull Long fromAccountId,
        @NotNull Long toAccountId,
        @NotNull @Positive BigDecimal amount
) {}