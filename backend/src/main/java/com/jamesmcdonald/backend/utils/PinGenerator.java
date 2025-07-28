package com.jamesmcdonald.backend.utils;

public class PinGenerator {

    public static int generatePin(){
        try {
            return Integer.parseInt(NumberGenerator.generateRandomDigitString(4));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Failed to generate a valid 4-digit PIN", e);
        }
    }
}
