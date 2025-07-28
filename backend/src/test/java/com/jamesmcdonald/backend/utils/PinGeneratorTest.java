package com.jamesmcdonald.backend.utils;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PinGeneratorTest {

    @Test
    void generatePin_shouldBeFourDigits() {
        String pin = PinGenerator.generatePin();
        assertEquals(
                4,
                pin.length(),
                "PIN should be 4 digits long");
    }

    @Test
    void generatePin_shouldBeNumeric() {
        String pin = PinGenerator.generatePin();
        int pinInt = Integer.parseInt(pin);

        assertTrue(
                pinInt >= 0 && pinInt <= 9999,
                "PIN should be a number between 0000 and 9999");
    }

    @RepeatedTest(5)
    void testPinsAreNotAlwaysSame() {
        String pin1 = PinGenerator.generatePin();
        String pin2 = PinGenerator.generatePin();
        // Not a strict test, but basic randomness check
        assertNotEquals(pin1, pin2, "Consecutive PINs should not be identical (most of the time)");
    }
}