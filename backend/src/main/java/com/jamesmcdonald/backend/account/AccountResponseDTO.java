package com.jamesmcdonald.backend.account;

import java.math.BigDecimal;

/**
 * Response DTO representing account details returned to the client.
 *
 * @param id         the unique identifier for the account
 * @param cardNumber the account's associated card number
 * @param pin        the personal identification number for the account
 * @param name       the full name of the account holder
 * @param email      the e-mail address of the account holder
 * @param phone      the contact phone number of the account holder
 * @param balance    the current balance of the account
 */
public record AccountResponseDTO(
        Long id,
        String cardNumber,
        String pin,
        String name,
        String email,
        String phone,
        BigDecimal balance
) {}
