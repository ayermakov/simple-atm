package com.atm;

public class Account {
    private String number;
    private String pin;
    private int balance;

    public Account(String a_number, String a_pin) {
        this(a_number, a_pin,  0);
    }

    public Account(String a_number, String a_pin, int a_balance) {
        number = a_number;
        pin = a_pin;
        balance = a_balance;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public void reduceBalance(int amount) {
        balance -= amount;
    }

    public class AccountOperationException extends Exception {
        public AccountOperationException(String message) {
            super(message);
        }
    }
}