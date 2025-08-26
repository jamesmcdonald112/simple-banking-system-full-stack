package com.jamesmcdonald.backend.utils;

/**
 * Utility class for generating secure 4-digit PINs for accounts.
 */
public class PinGenerator {

    /**
     * Generates a random 4-digit numeric PIN.
     *
     * @return a 4-digit PIN as a String
     * @throws IllegalStateException if the PIN generation fails
     */
    public static String generatePin(){
        try {
            return NumberGenerator.generateRandomDigitString(4);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Failed to generate a valid 4-digit PIN", e);
        }
    }
}
