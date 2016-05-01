package com.atm;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts = new ArrayList<Account>();

    public Bank(Account... newAccounts) {
        for(Account account : newAccounts) {
            accounts.add(account);
            System.out.format("\n\tCard number %s, pin %s, %d UAH.", account.getNumber(), account.getPin(), account.getBalance());
        }
    }

    public boolean login(String cardNumber, String pinCode) {
        for(Account account : accounts) {
            if(account.getNumber().equals(cardNumber) && account.getPin().equals(pinCode)) {
                return true;
            }
        }
        return false;
    }
}