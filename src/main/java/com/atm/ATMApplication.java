package com.atm;

import java.util.Scanner;
import java.io.Console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ATMApplication {
	private static Scanner scanner = new Scanner(System.in);

	private static String readString() {
		return scanner.nextLine();
	}

	private static String readString(String prompt) {
		System.out.print(prompt);
		return readString();
	}

	private static int readInt() {
		return scanner.nextInt();
	}

	private static int readInt(String prompt) {
		System.out.print(prompt);
		return readInt();
	}

	public static void main(String[] args) {
		System.out.print("Welcome! Simple session-based ATM. Set of your accounts to play around:");		
		Account firstAccount = new Account("44445555", "6666", 233);
		Account secondAccount = new Account("11112222", "3333", 478);
		Bank bank = new Bank(firstAccount, secondAccount);
		System.out.println("\n\tAttention! All data will be lost when you cancel the program execution.\n");

		Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        
		boolean anotherTransaction = true;
		while(anotherTransaction) {
			String number = "", pin = "";
			do {	
				try {
					number = readString("Your card number: ");
		    		pin = readString("Your pin: ");
				} catch(Exception ex) {
					ex.printStackTrace();
					System.err.println(ex);
				}
			} while(!bank.login(number, pin));
		    // остаток на счету, положить деньги на счет, снять деньги со счета, перевести деньги на другой счет, 
			anotherTransaction = false;
		}
	
		scanner.close();		
	}
}