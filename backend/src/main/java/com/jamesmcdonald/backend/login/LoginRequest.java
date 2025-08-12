package com.jamesmcdonald.backend.login;

public class LoginRequest {

    private String cardNumber;
    private String pin;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String cardNumber, String pin, String password) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
