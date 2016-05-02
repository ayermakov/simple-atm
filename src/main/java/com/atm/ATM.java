package com.atm;

import java.util.Hashtable;
import java.util.Arrays;

public class ATM {
    public static final int[] noteTypes = {200, 100, 50, 20, 10, 5, 2, 1};
    private Bank bank;
    private Terminal terminal;
    private Hashtable<Integer, Integer> banknotes = new Hashtable<Integer, Integer>(noteTypes.length);
    private String currentAccountNumber;

    public ATM(Bank a_bank, Terminal io) {
        bank = a_bank;
        terminal = io;

        for(int type : noteTypes) {
            banknotes.put(type, 0);
        }
    }

    public ATM(Bank a_bank, Terminal io, int[] notesQuantity) {
        this(a_bank, io);

        if(notesQuantity.length == noteTypes.length) {
            for(int i = 0; i < notesQuantity.length; ++i) {
                banknotes.put(noteTypes[i], notesQuantity[i]);
            }

            printAvailableBanknotes();
        } else {
        	terminal.println("No notes inside. Check configuration.");
        }
    }

    public boolean login(String cardNumber, String pinCode) {
        if(bank.login(cardNumber, pinCode)) {
            currentAccountNumber = cardNumber;
            return true;
        }

        return false;
    }

    public void logout() {
    	currentAccountNumber = null;
    }

    public boolean hasActiveSession() {
    	if(currentAccountNumber == null) {
    		return false;
    	} else {
    		return true;
    	}
    }

    public boolean insertBanknote(int note) {
        if(validateBanknote(note)) {
            banknotes.put(note, banknotes.get(note) + 1); 
            return true;
        }
        terminal.println("Unknown banknote. Please enter correct note ...");
        return false;
    }

    public boolean validateBanknote(int banknote) {
        for(int note : noteTypes) {
            if(note == banknote) {
                return true;
            }
        }

        return false;
    }

    public void printAvailableBanknotes() {
        terminal.print("Notes inside ATM: ");
        for(Integer note : banknotes.keySet()) {
            terminal.print(note + " => " + banknotes.get(note) + "; ");
        }
        terminal.println();
    }

    public void showBalance() {
    	if(currentAccountNumber != null) {
    		terminal.println(bank.getAccountInfo());
    	}
    }

    public void withdraw(int amount) {
    	if(bank.isWithdrawalPossible(amount)) {
    		int[] chosenNotes = chooseBanknotesToWithdraw(amount);
    		if(chosenNotes.length == 0) {
    			terminal.println("Not enough notes to complete your request. Please, try later.");
    			return;
    		}

    		if(bank.performWithdrawal(amount)) {
				for(int i = 0; i < noteTypes.length; ++i) {
		            banknotes.put(noteTypes[i], banknotes.get(noteTypes[i]) - chosenNotes[i]);
		        }
		        terminal.print("Success! ");
		        showBalance();
		        printAvailableBanknotes();
    		} else {
    			terminal.println("Bank failed to perform this transaction. Please, try again next time.");
    		}
    	} else {
    		terminal.println("Bank denied this withdrawal.");
    	}
    }

    private int[] chooseBanknotesToWithdraw(int requestedMoney) {
    	int[] returningNotes = new int[noteTypes.length];
    	Arrays.fill(returningNotes, 0);

    	int total = 0;
    	int expectedAmount = requestedMoney;
    	for(int i = 0; i < noteTypes.length; ++i) {
    		int type = noteTypes[i];

    		if(requestedMoney < type || banknotes.get(type) == 0) {
    			continue;
    		}
    		
			int ableToWithdraw = (requestedMoney / type);
			if(banknotes.get(type) >= ableToWithdraw) {
				returningNotes[i] += ableToWithdraw;
				total += ableToWithdraw * type;
				requestedMoney -= ableToWithdraw * type;
			} else {
				returningNotes[i] += banknotes.get(type);
				total += banknotes.get(type) * type;
				requestedMoney -= banknotes.get(type) * type;
			}
    		
    		// terminal.println("Requested array: " + Arrays.toString(returningNotes));
    		// terminal.println("Total: " + Integer.toString(total));
    		if(total == expectedAmount) {
    			return returningNotes;
    		}
    	}

    	return new int[0];
    }
}