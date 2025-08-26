package com.jamesmcdonald.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for generating valid 16-digit card numbers.
 * <p>
 * This class provides methods to generate card numbers that conform to standard formats,
 * including a valid Bank Identification Number (BIN), a random customer account number,
 * and a checksum digit calculated using the Luhn algorithm.
 */
public class CardNumberGenerator {

    private static final Logger log = LoggerFactory.getLogger(CardNumberGenerator.class);

    /**
     * Generates a valid 16-digit card number as a String.
     * The number consists of a BIN, a customer account number, and a Luhn checksum digit.
     *
     * @return the 16-digit card number as a String
     */
    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();

        // Generate BIN and append
        sb.append(generateBin());

        // Generate customer account number and append
        sb.append(generateCustomerAccountNumber());

        // Generate checksum and append
        int checksum = generateChecksum(sb.toString());
        sb.append(checksum);

        String cardNumber = sb.toString();
        log.debug("Generated card number {}", cardNumber);

        return cardNumber;
    }

    /**
     * Generates the Bank Identification Number (BIN) for the card.
     *
     * @return the BIN as a String
     */
    private static String generateBin() {
        return "400000";
    }

    /**
     * Generates a random customer account number as a String.
     *
     * @return the customer account number as a String
     */
    private static String generateCustomerAccountNumber() {
        return NumberGenerator.generateRandomDigitString(9);
    }

    /**
     * Calculates the checksum digit for the card number using the Luhn algorithm.
     *
     * @param accountIdentifierAndBin The concatenated BIN and customer account number
     * @return the checksum digit as an integer
     */
    private static int generateChecksum(String accountIdentifierAndBin) {
        return LuhnUtils.calculateChecksum(accountIdentifierAndBin);
    }
}
