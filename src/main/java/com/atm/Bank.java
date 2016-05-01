package com.atm;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private String currAccountNumber = null;

    public Bank(Account... newAccounts) {
        for(Account account : newAccounts) {
            accounts.add(account);
            System.out.format("\tCard number %s, pin %s, %d UAH.\n", account.getNumber(), account.getPin(), account.getBalance());
        }
    }

    public boolean login(String cardNumber, String pinCode) {
        for(Account account : accounts) {
            if(account.getNumber().equals(cardNumber) && account.getPin().equals(pinCode)) {
            	currAccountNumber = cardNumber;
                return true;
            }
        }
        return false;
    }

    public String getAccountInfo() {
	    if(currAccountNumber != null) {
	    	for(Account account : accounts) {
	    		if(account.getNumber().equals(currAccountNumber)) {
	    			return "Account number " + currAccountNumber + ", your balance is " + account.getBalance() + " UAH.";
	    		}
	    	}
    	}
    	return "No data about the client.";
    }
}