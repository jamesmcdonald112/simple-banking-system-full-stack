package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.testUtils.AccountTestUtils;
import com.jamesmcdonald.backend.utils.LuhnUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Account} class to verify creation, equality, and identity logic.
 */
class AccountTest {

    /**
     * Ensures that newly created accounts have valid card numbers, pins, and a zero balance
     */
    @Test
    void create_whenCalled_returnsAccountWithValidCardNumberAndPin() {
        Account account = Account.create();

        assertNotNull(
                account.getCardNumber(),
                "Card number should not be null");

        assertTrue(
                LuhnUtils.isValid(account.getCardNumber()),
                "Card number should pass the Luhn isValid method");

        assertTrue(
                account.getPin().matches("\\d{4}"),
                "PIN should be a 4-digit numeric string");

        assertEquals(
                0,
                account.getBalance(),
                "Newly created accounts should have a balance of 0");
    }

    /**
     * Ensures that accounts with the same card number are equal.
     */
    @Test
    void comparingAccounts_withTheSameCardNumber_shouldBeEqual() {
        Account account1 = Account.create();
        Account account2 = Account.create();

        try {
            AccountTestUtils.overrideCardNumber(account2, account1.getCardNumber());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to override card number via reflection", e);
        }

        assertEquals(
                account1,
                account2,
                "Accounts with the same card number should be equal");

        assertEquals(
                account1.hashCode(),
                account2.hashCode(),
                "Equal accounts must have the same hashCode");
    }

    /**
     * Ensures that different accounts are not equal.
     */
    @Test
    void comparingAccounts_withDifferentCardNumbers_shouldNotBeEqual() {
        Account account1 = Account.create();
        Account account2 = Account.create();

        assertNotEquals(
                account1,
                account2,
                "Different accounts should not be considered equal"
        );

        assertNotEquals(
                account1.hashCode(),
                account2.hashCode(),
                "Different accounts must have different hashCodes"
        );
    }

    /**
     * Ensures the toString method contains the id, card number and the balance
     */
    @Test
    void teoString_shouldContainCardNumberAndBalance() {
        Account account = Account.create();
        String result = account.toString();

        assertTrue(
                result.contains(String.valueOf(account.getId())),
                "The toString() method should contain the id"
        );
        assertTrue(
                result.contains(account.getCardNumber()),
                "The toString() method should contain the card number");

        assertTrue(
                result.contains(String.valueOf(account.getBalance())),
                "The toString() method should contain the balance"
        );
    }
}