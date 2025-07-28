package com.jamesmcdonald.backend.login;

public class AccountResponse {
    private Long id;
    private String cardNumber;
    private int balance;

    public AccountResponse(Long id, String cardNumber, int balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }
}
