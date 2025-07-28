package com.jamesmcdonald.backend.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Number Generator.
 * Verifies that generated values are digit-only, correct in length, and different on consecutive
 * calls.
 */
class NumberGeneratorTest {

    private int length = 8;
    private final int MAX_LENGTH = 19;
    private String result;

    @BeforeEach
    void setUp() {
        length = 8;
        result = NumberGenerator.generateRandomDigitString(length);
    }

    @Test
    void testGeneratedLength() {
        assertEquals(length, result.length(),
                "Generated number should have a length of " + length + " digits long");
    }

    @Test
    void testOnlyContainsDigits() {
        assertTrue(result.matches("\\d+"),
                "Expected only digits [\\d+], but got: " + result);
    }

    @Test
    void testResultIsNotNullOrBlank() {
        assertNotNull(result, "Expected result to be not null");
        assertFalse(result.isBlank(), "Expected result to be not blank");
    }

    @Test
    void testMinimumLength() {
        result = NumberGenerator.generateRandomDigitString(1);
        assertEquals(1, result.length(), "1-digit string should have length of 1");
    }

    @Test
    void testMaximumLength() {
        result = NumberGenerator.generateRandomDigitString(MAX_LENGTH);
        assertEquals(MAX_LENGTH, result.length(), "Max length string should be " + MAX_LENGTH + " digits");
    }

    @Test
    void testValuesAreNotRepeated() {
        String result2 = NumberGenerator.generateRandomDigitString(length);
        assertNotEquals(result, result2,
                "Consecutive calls should produce different results.\nResult 1: "
                        + result + "\nResult 2: " + result2);
    }
}