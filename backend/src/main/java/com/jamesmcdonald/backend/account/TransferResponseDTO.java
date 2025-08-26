package com.jamesmcdonald.backend.account;

import java.math.BigDecimal;

/**
 * Represents the outcome of a transfer operation between two accounts.
 *
 * @param fromAccountId the ID of the account from which the amount was transferred
 * @param toAccountId the ID of the account to which the amount was transferred
 * @param amount the amount of money transferred
 * @param fromBalance the balance of the from account after the transfer
 * @param toBalance the balance of the to account after the transfer
 */
public record TransferResponseDTO(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        BigDecimal fromBalance,
        BigDecimal toBalance
) {}