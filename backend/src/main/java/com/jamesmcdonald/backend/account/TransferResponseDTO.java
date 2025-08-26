package com.jamesmcdonald.backend.account;

import java.math.BigDecimal;

public record TransferResponseDTO(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        BigDecimal fromBalance,
        BigDecimal toBalance
) {}