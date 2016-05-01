package com.atm;

import java.util.Hashtable;

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
        }
    }

    public boolean login(String cardNumber, String pinCode) {
        if(bank.login(cardNumber, pinCode)) {
            currentAccountNumber = cardNumber;
            return true;
        }

        return false;
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
}