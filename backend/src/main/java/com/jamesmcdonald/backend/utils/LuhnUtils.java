package com.jamesmcdonald.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * A stateless utility class providing methods to validate and generate card numbers using the Luhn algorithm.
 * <p>
 * All methods in this class are static and thread-safe, making it suitable for use in concurrent environments.
 * This utility is intended for use in generating or validating payment card numbers and similar identification numbers.
 * </p>
 */
public class LuhnUtils {

    private static final Logger log = LoggerFactory.getLogger(LuhnUtils.class);

    /**
     * Applies the Luhn algorithm to check whether a card number is valid.
     * The last digit is treated as a checksum and used to verify the preceding digits.
     *
     * @param cardNumber The card number to check as a String
     * @return True if valid; false otherwise
     */
    public static boolean isValid(String cardNumber) {
        validateNumericInput(cardNumber, "Card number");

        int[] digits = convertCardAsStringToIntArray(cardNumber);
        int checksumDigit = digits[digits.length - 1];
        // Remove the checksum digit for calculation
        digits = Arrays.copyOf(digits, digits.length - 1);

        // Keep a running total
        int total = 0;

        // iterate through all numbers, double the odd digits, and add all to the total
        for (int i = 0; i < digits.length; i++) {
            int currentDigit = digits[i];

            // Double digits at even indexes (0-based)
            if (i % 2 == 0) currentDigit *= 2;

            // Subtract 9 from values greater than 9
            if(currentDigit > 9) currentDigit -= 9;

            total += currentDigit;
        }

        boolean isValidChecksum = (total + checksumDigit) % 10 == 0;
        log.debug("Luhn validation result for card [{}]: {}", cardNumber, isValidChecksum);

        return isValidChecksum;
    }

    /**
     * Calculates the Luhn checksum digit for a given partial card number (usually BIN + account identifier).
     * <p>
     * The Luhn algorithm is used to validate identification numbers like credit card numbers. This method
     * implements the core algorithm to generate the final checksum digit that would make the full number valid.
     * </p>
     *
     * <p>
     * Algorithm steps:
     * <ul>
     *   <li>Convert the input string to an array of digits</li>
     *   <li>Double every digit at even index (0-based)</li>
     *   <li>If a doubled digit exceeds 9, subtract 9 from it</li>
     *   <li>Sum all the processed digits</li>
     *   <li>Calculate the checksum so that (sum + checksum) % 10 == 0</li>
     * </ul>
     * </p>
     *
     * @param accountIdentifierAndBin A string of 15 digits representing the card number without the checksum.
     * @return The checksum digit (0–9) that should be appended to make the full number valid.
     */
    public static int calculateChecksum(String accountIdentifierAndBin) {
        validateNumericInput(accountIdentifierAndBin, "Account identifier and BIN");

        int[] digits = convertCardAsStringToIntArray(accountIdentifierAndBin);

        int total = 0;

        // iterate through all numbers, double the odd digits, and add all to the total
        for (int i = 0; i < digits.length; i++) {
            int currentDigit = digits[i];

            // Double digits at even indexes (0-based)
            if (i % 2 == 0) currentDigit *= 2;

            // Subtract 9 from values greater than 9
            if(currentDigit > 9) currentDigit -= 9;

            total += currentDigit;
        }

        int checksum = (10 -(total % 10)) % 10;
        log.debug("Calculated Luhn checksum for [{}] is {}", accountIdentifierAndBin, checksum);

        return checksum;
    }

    /**
     * Converts a card number string into an array of its individual digits.
     *
     * @param cardNumber The credit card number as a string (e.g., "4000008449433403")
     * @return An array of integers representing each digit in the card number
     */
    private static int[] convertCardAsStringToIntArray(String cardNumber) {
        char[] chars = cardNumber.toCharArray();
        int[] digits = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            digits[i] = Character.getNumericValue(chars[i]);
        }
        return digits;
    }

    /**
     * Validates that the given input string is non-null and contains only numeric characters.
     *
     * @param input     The string to validate.
     * @param fieldName The name of the field being validated (used for logging and error messages).
     * @throws IllegalArgumentException if the input is null or contains non-numeric characters.
     */
    private static void validateNumericInput(String input, String fieldName) {
        if (input == null || !input.matches("\\d+")) {
            log.warn("[{}] must be non-null and numeric: {}", fieldName, input);
            throw new IllegalArgumentException(fieldName + " must be a non-null numeric string");
        }
    }
}
