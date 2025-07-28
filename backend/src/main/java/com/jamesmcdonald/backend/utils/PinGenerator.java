package com.jamesmcdonald.backend.utils;

public class PinGenerator {

    public static String generatePin(){
        try {
            return NumberGenerator.generateRandomDigitString(4);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Failed to generate a valid 4-digit PIN", e);
        }
    }
}
