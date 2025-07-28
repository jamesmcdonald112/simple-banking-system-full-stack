package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.utils.CardNumberGenerator;
import com.jamesmcdonald.backend.utils.PinGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Represents a simple bank account with a unique card number, secure pin,
 * and a balance that starts at zero. Used as a persistent JPA entity.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private final String cardNumber;

    @Column(nullable = false)
    private final int pin;

    @Column(nullable = false)
    private int balance;

    /**
     * Required by JPA. Do not use directly; use {@link #create()} instead.
     */
    @Deprecated
    public Account() {
        // Placeholders to satisfy JPA for final instance variables.
        this.cardNumber = null;
        this.pin = -1;
    }

    /**
     * Creates a new account with a card number, PIN, and balance of zero.
     *
     * @param cardNumber new card number
     * @param pin new pin
     * @param balance balance of 0
     */
    private Account(String cardNumber, int pin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    /**
     * Creates a new account with a generated card number, PIN, and balance of zero.
     *
     * @return a new Account instance with unique cardNumber and PIN
     */
    public static Account create() {
        return new Account(
                CardNumberGenerator.generateCardNumber(),
                PinGenerator.generatePin(),
                0
        );
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    // -- Object method overrides --

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(this.cardNumber, account.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.cardNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + this.id +
                ", cardNumber='" + this.cardNumber + '\'' +
                ", balance=" + this.balance +
                '}';
    }
}
