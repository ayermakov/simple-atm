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
	    	Account foundAccount = findAccount(currAccountNumber);
	    	if(foundAccount != null) {
	    		return "Account number " + foundAccount.getNumber() + ", your balance is " + foundAccount.getBalance() + " UAH.";
    		}
    	}

    	return "No data about the client.";
    }

    private Account findAccount(String thatAccountNumber) {
		for(Account account : accounts) {
    		if(account.getNumber().equals(thatAccountNumber)) {
    			return account;
    		}
    	}

    	return null;
    }

    public boolean performWithdrawal(int amount) {
    	if(currAccountNumber != null) {
    		Account foundAccount = findAccount(currAccountNumber);
    		if(isWithdrawalPossible(foundAccount, amount)) {
    			foundAccount.reduceBalance(amount);
    			return true;
    		}
    	}

    	return false;
    }

    public boolean isWithdrawalPossible(int amount) {
    	Account foundAccount = findAccount(currAccountNumber);
	    if(isWithdrawalPossible(foundAccount, amount)) {
			return true;
    	}

    	return false;
    }

    private boolean isWithdrawalPossible(Account someAccount, int amount) {
		if(someAccount != null && someAccount.getBalance() >= amount && validCashAmount(amount)) {
			return true;
		}

    	return false;
    }

    private boolean validCashAmount(int amount) {
        if(amount >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean putIntoAccount(int amount) {
    	if(currAccountNumber != null && validCashAmount(amount)) {
    		Account foundAccount = findAccount(currAccountNumber);
    		if(Integer.MAX_VALUE - foundAccount.getBalance() > amount) {
    			foundAccount.addBalance(amount);
    			return true;
    		}
    	}

    	return false;
    }

    public boolean transfer(int amount, String targetAccountNumber) {
    	if(currAccountNumber != null && targetAccountNumber != null && validCashAmount(amount)) {
    		Account origin = findAccount(currAccountNumber);
    		Account target = findAccount(targetAccountNumber);
    		if(origin != target && isWithdrawalPossible(origin, amount) && (Integer.MAX_VALUE - target.getBalance() > amount)) {
    			origin.reduceBalance(amount);
    			target.addBalance(amount);
    			return true;
    		}
    	}

    	return false;
    }
}