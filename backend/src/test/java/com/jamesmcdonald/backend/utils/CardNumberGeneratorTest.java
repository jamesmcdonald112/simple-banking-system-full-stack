package com.jamesmcdonald.backend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberGeneratorTest {

    /**
     * Verifies the card starts with the correct BIN number
     */
    @Test
    void generateCardNumber_cardShouldStartWithBin() {
        String card = CardNumberGenerator.generateCardNumber();
        assertTrue(card.startsWith("400000"),
                "Card number should start with BIN 400000");
    }

    /**
     * Verifies the card is 16-digits long and only numeric characters
     */
    @Test
    void generateCardNumber_cardLengthShouldBe16Digits(){
        String card = CardNumberGenerator.generateCardNumber();

        assertTrue(card.matches("\\d+"),
                "Card only contains numeric characters. Card characters: " + card);

        assertEquals(16,
                card.length(),
                "Card number should be 16 digits.");
    }

    @Test
    void generateCardNumber_cardNumberShouldPassLuhnCheck() {
        String card = CardNumberGenerator.generateCardNumber();
        assertTrue(LuhnUtils.isValid(card),"" +
                "Generated card number should pass the Luhn check");
    }


}