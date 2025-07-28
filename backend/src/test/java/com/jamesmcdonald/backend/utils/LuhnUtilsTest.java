package com.jamesmcdonald.backend.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LuhnUtilsTest {

    /**
     * Validates that valid card numbers pass the Luhn check.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "4000008449433403",
            "4000001234567899",
            "4000000000000002"
    })
    void validCardNumbersShouldPassLuhnCheck(String cardNumber) {
        assertTrue(LuhnUtils.isValid(cardNumber), "Expected card to be valid: " + cardNumber);
    }

    /**
     * Validates that invalid card numbers fail the Luhn check.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "4000008449433404",
            "4000001234567890",
            "4000000000000003"
    })
    void invalidCardNumbersShouldFailLuhnCheck(String cardNumber) {
        assertFalse(LuhnUtils.isValid(cardNumber), "Expected card to be invalid: " + cardNumber);
    }

    /**
     * Validates that the checksum calculated from a 15-digit input
     * produces a 16-digit card number that passes the Luhn check.
     */
    @Test
    public void testCalculateChecksum() {
        String accountIdentifierAndBin = "400000844943340";
        int checksum = LuhnUtils.calculateChecksum(accountIdentifierAndBin);

        String validCardNumber = accountIdentifierAndBin + checksum;

        assertTrue(LuhnUtils.isValid(validCardNumber), "Expect card to be valid: " + validCardNumber);
    }
}