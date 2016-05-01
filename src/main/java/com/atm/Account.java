package com.atm;

public class Account {
	private String number;
	private String pin;
	private int balance;

	public Account(String a_number, String a_pin) {
		this(a_number, a_pin,  0);
	}

	public Account(String a_number, String a_pin, int a_balance) {
		// Card number and pin may also be validated first.
		number = a_number;
		pin = a_pin;

		if(validCashAmount(a_balance)) {
			balance = a_balance;
		} else {
			balance = 0;
		}
	}

	private boolean validCashAmount(int amount) {
		if(amount >= 0) {
			return true;
		} else {
			return false;
		}
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

	public void addBalance(int amount) throws AccountOperationException {
		if(Integer.MAX_VALUE - amount < balance) {
			
		}

		if(validCashAmount(amount)) {
			throw new AccountOperationException("Not valid amount: " + amount + ".");	
		}

		balance += amount;
	}

	public void reduceBalance(int amount) throws AccountOperationException {
		if(balance - amount < 0) {
			throw new AccountOperationException("Too little balance.");	
		}

		if(validCashAmount(amount)) {
			throw new AccountOperationException("Not valid amount: " + amount + ".");	
		}

		balance -= amount;
	}

	public class AccountOperationException extends Exception {
		public AccountOperationException(String message) {
        	super(message);
    	}
	}
}