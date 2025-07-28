package com.jamesmcdonald.backend.utils;

public class CardNumberGenerator {

    /**
     * Generates the 16 digit card number.
     *
     * @return 16-digit Card number as a String
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

        return sb.toString();
    }

    /**
     * Generates the BIN for the card
     *
     * @return The BIN number as a String
     */
    private static String generateBin() {
        return "400000";
    }

    /**
     * Generates a random customer account number
     *
     * @return the customer account number as a string
     */
    private static String generateCustomerAccountNumber() {
        return NumberGenerator.generateRandomDigitString(9);
    }

    /**
     * Generates the checksum for the customer account number
     *
     * @return the checksum as a string
     */
    private static int generateChecksum(String accountIdentifierAndBin) {
        return LuhnUtils.calculateChecksum(accountIdentifierAndBin);
    }
}
