package com.jamesmcdonald.backend.login;

import java.math.BigDecimal;

/**
 * Data Transfer Object returned on successful login containing account details.
 *
 * @param id the unique identifier of the account
 * @param cardNumber the card number associated with the account
 * @param balance the current balance of the account
 * @param name the name of the account holder
 * @param email the email address of the account holder
 * @param phone the phone number of the account holder
 */
public record AccountResponse(
         Long id,
         String cardNumber,
         BigDecimal balance,
         String name,
         String email,
         String phone
) {
}
