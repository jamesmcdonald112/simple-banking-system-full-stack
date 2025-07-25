package com.jamesmcdonald.backend.testUtils;

import com.jamesmcdonald.backend.account.Account;

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
}
