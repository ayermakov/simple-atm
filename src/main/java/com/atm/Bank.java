package com.atm;

public class Bank {
	public Bank(Account... accounts) {
		for(Account account : accounts) {
			System.out.format("\n\tCard number %s, pin %s, %d UAH.", account.getNumber(), account.getPin(), account.getBalance());
		}
	}

	public void addAccount(Account newAccount) {

	}

	public boolean login(String cardNumber, String pinCode) {
		return true;
	}
}