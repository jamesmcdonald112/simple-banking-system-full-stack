package com.jamesmcdonald.backend.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDTO(
        @NotNull Long fromAccountId,
        @NotNull Long toAccountId,
        @NotNull @Positive BigDecimal amount
) {}