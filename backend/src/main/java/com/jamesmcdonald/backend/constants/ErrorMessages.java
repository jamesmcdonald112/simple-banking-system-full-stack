package com.jamesmcdonald.backend.constants;

/**
 * Centralised constants for error messages.
 */
public class ErrorMessages {
    // Used when an account cannot be found
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    // Used when card number, PIN or password is invalid
    public static final String INVALID_CARD_OR_PIN_OR_PASSWORD = "Invalid card number, PIN or " +
            "password";
    // Used when the email is already registered
    public static final String EMAIL_ALREADY_IN_USE = "Email already in use.";
}
