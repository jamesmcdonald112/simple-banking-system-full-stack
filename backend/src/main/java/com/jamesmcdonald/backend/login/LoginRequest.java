package com.jamesmcdonald.backend.login;

public class LoginRequest {

    private String cardNumber;
    private String pin;

    public LoginRequest() {
    }

    public LoginRequest(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
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
}
