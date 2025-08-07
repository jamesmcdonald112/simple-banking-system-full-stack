package com.jamesmcdonald.backend.testUtils;

import com.jamesmcdonald.backend.account.Account;
import java.util.List;
import java.lang.reflect.Field;

public class AccountTestUtils {
    /**
     * Overrides the card number of the selected account via reflection.
     *
     * @param target The account instance to modify
     * @param newCardNumber The card number to inject
     * @throws ReflectiveOperationException if the field cannot be accessed
     */
    public static void overrideCardNumber(Account target, String newCardNumber) throws ReflectiveOperationException {
        Field cardNumberField = Account.class.getDeclaredField("cardNumber");
        cardNumberField.setAccessible(true);
        cardNumberField.set(target, newCardNumber);
    }

    public static Account generateTestAccount() {
        return Account.create("John Doe", "johndoe1234@example.com", "+353852637489",
                "password123");
    }

    public static List<Account> generateTwoTestAccounts() {
        Account account1 = Account.create("John Doe", "johndoe1234@example.com", "+353852637489", "password123");
        Account account2 = Account.create("Jane Doe", "janedoe1234@example.com", "+353850987654", "1234password");
        return List.of(account1, account2);
    }
}
