package com.jamesmcdonald.backend.login;

public class AccountResponse {
    private Long id;
    private String cardNumber;
    private int balance;
    private String name;
    private String email;
    private String phone;


    public AccountResponse(Long id, String cardNumber, int balance, String name, String email, String phone) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
