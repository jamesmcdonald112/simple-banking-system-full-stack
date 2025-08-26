package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.utils.CardNumberGenerator;
import com.jamesmcdonald.backend.utils.PinGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
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
    private final String pin;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;


    /**
     * Required by JPA. Do not use directly; use {@link #create(String, String, String, String)} instead.
     */
    @Deprecated
    public Account() {
        // Placeholders to satisfy JPA for final instance variables.
        this.cardNumber = null;
        this.pin = null;
    }


    /**
     * Creates a new account with a card number, pin, balance, name, email, phone, and password.
     * Used internally for account creation.
     */
    private Account(String cardNumber, String pin, BigDecimal balance, String name, String email,
                    String phone, String password) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Creates a new account with a generated card number, PIN, balance of zero, name, email,
     * phone, and password.
     *
     * @return a new Account instance with unique cardNumber and PIN
     */
    public static Account create(String name, String email, String phone, String password) {
        return new Account(
                CardNumberGenerator.generateCardNumber(),
                PinGenerator.generatePin(),
                BigDecimal.ZERO,
                name,
                email,
                phone,
                password
        );
    }

    public void addAmount(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        this.balance = this.balance.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
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
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
