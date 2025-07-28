package com.jamesmcdonald.backend.utils;

public class InputParser {

    /**
     * Parses an integer from the string, or returns fallback on failure.
     *
     * @param input the user input
     * @param fallback the value to return if parsing fails
     * @return parsed int or fallback
     */
    public static int safeParseInt(String input, int fallback) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
