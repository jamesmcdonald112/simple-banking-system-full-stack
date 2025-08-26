package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.testUtils.AccountTestUtils;
import com.jamesmcdonald.backend.utils.LuhnUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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
        Account account = AccountTestUtils.generateTestAccount();

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
                BigDecimal.ZERO,
                account.getBalance(),
                "Newly created accounts should have a balance of 0");
    }

    /**
     * Ensures that accounts with the same card number are equal.
     */
    @Test
    void comparingAccounts_withTheSameCardNumber_shouldBeEqual() {
        List<Account> mockList = AccountTestUtils.generateTwoTestAccounts();
        Account account1 = mockList.get(0);
        Account account2 = mockList.get(1);

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
        List<Account> mockList = AccountTestUtils.generateTwoTestAccounts();
        Account account1 = mockList.get(0);
        Account account2 = mockList.get(1);

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
    void toString_shouldContainCardNumberAndBalance() {
        Account account = AccountTestUtils.generateTestAccount();
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

    @Test
    void create_whenCalled_setsBalanceToZeroBigDecimal() {
        Account account = AccountTestUtils.generateTestAccount();
        assertEquals(0, account.getBalance().compareTo(new BigDecimal("0.00")));
    }

    @Test
    void addAmount_withPositiveValue_shouldIncreaseBalance() {
        Account account = AccountTestUtils.generateTestAccount();

        // start at 0.00
        assertEquals(0, account.getBalance().compareTo(new BigDecimal("0.00")));

        account.addAmount(new BigDecimal("10.50"));

        // expect 10.50 after deposit
        assertEquals(0, account.getBalance().compareTo(new BigDecimal("10.50")));
    }

    @Test
    void addAmount_withNegativeValue_shouldThrow() {
        Account account = AccountTestUtils.generateTestAccount();
        assertThrows(IllegalArgumentException.class,
                () -> account.addAmount(new BigDecimal("-0.01")));
    }


    @Test
    void subtractAmount_withPositiveValue_shouldDecreaseBalance() {
        Account a = AccountTestUtils.generateTestAccount();
        a.addAmount(new BigDecimal("50.00"));
        a.subtractAmount(new BigDecimal("20.00"));
        assertEquals(0, a.getBalance().compareTo(new BigDecimal("30.00")));
    }

    @Test
    void subtractAmount_withNonPositive_shouldThrow() {
        Account a = AccountTestUtils.generateTestAccount();
        assertThrows(IllegalArgumentException.class,
                () -> a.subtractAmount(new BigDecimal("0.00")));
    }

    @Test
    void subtractAmount_insufficientFunds_shouldThrow() {
        Account a = AccountTestUtils.generateTestAccount();
        a.addAmount(new BigDecimal("10.00"));
        assertThrows(IllegalArgumentException.class,
                () -> a.subtractAmount(new BigDecimal("10.01")));
    }

}