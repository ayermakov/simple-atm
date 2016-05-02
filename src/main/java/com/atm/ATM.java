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

    public void putIntoAccount(int amount) {
    	int total = 0;
    	while(total < amount) {
    		int number = 0;
	    	try {	
	    		String banknote = terminal.readString("\tInsert banknote: ");
	    		number = Integer.parseInt(banknote);
	    	} catch(Exception ex) {
	    		terminal.println("Wrong input, try again.");
	    		continue;
	    	} 

            if(validateBanknote(number)) {
            	if(bank.putIntoAccount(number)) {
	            	total += number;	
	            	insertBanknote(number);
            	} else {
            		terminal.println("Bank has not accepted the banknote. Please, try again.");
            	}
            }
    	}

    	terminal.println("Inserted " + total + " UAH.");
    }

    private boolean validateBanknote(int banknote) {
        for(int note : noteTypes) {
            if(note == banknote) {
                return true;
            }
        }

        terminal.println("Unknown banknote. Please insert correct one ...");
        return false;
    }

    private void insertBanknote(int note) {
        banknotes.put(note, banknotes.get(note) + 1); 
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
    		
    		if(total == expectedAmount) {
    			return returningNotes;
    		}
    	}

    	return new int[0];
    }

    public void transfer() {
    	if(currentAccountNumber != null) {
	    	while(true) {
	    		try {
		    		String input = terminal.readString("What amount of money you want to transfer: ");
		    		int amount = Integer.parseInt(input);
		    		input = terminal.readString("Enter a destination account number: ");

		    		if(bank.transfer(amount, input)) {
		    			terminal.println("Success!");
		    		} else {
		    			terminal.println("Bank denied this transfer.");
		    		}

		    		return;
		    	} catch(Exception ex) {
		    		terminal.println("Wrong input, try again.");
		    	} 
		    }
    	}
    }
}