package com.atm;

import java.util.InputMismatchException;

public class ATMApplication {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.print("Welcome! Simple session-based ATM. Set of your accounts to play around:\n");        
        
        Account firstAccount = new Account("44445555", "6666", 233);
        Account secondAccount = new Account("11112222", "3333", 478);
        Bank bank = new Bank(firstAccount, secondAccount);

        ATM atm = new ATM(bank, terminal, new int[]{2, 1, 1, 1, 1, 1, 1, 1});

        terminal.println("\n\tAttention! All data will be lost when you exit the program.\n");

        String turnOff = "";
        do {
            String number = "", pin = "";
            boolean accepted;
            do {    
                accepted = false;
                try {
                    number = terminal.readString("Enter card number: ");
                    pin = terminal.readString("Enter pin: ");

                    accepted = atm.login(number, pin);

                    if(accepted) {
                        terminal.println("Success!");
                    } else{
                        terminal.println("Wrong card number or pin! Try again ...");
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                    System.err.println("Input/output exception raised, try again ...");
                }
            } while(!accepted); 

            terminal.println("Choose action:\n\t[1] Show my account balance.\n\t[2] Withdraw money.\n\t[3] Put money into account.\n\t[4] Transfer.\n\t[5] Logout.");
            
            do {
            	int actionChoice = 0;
                try {
                    String temp = terminal.readString("Which action to do next (1-5): ");
                    actionChoice = Integer.parseInt(temp);

                    switch(actionChoice) {
                        case 1:
                        	atm.showBalance();
                            break; 
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4: 
                            break;
                        case 5:
                        	atm.logout();
                            terminal.println("Your session is closed ..."); 
                            break;
                        default:
                            System.err.println("Wrong choice ... try again from 1 to 5.");
                    }
                } catch(InputMismatchException ime) {
                    System.err.println("Input mismatch exception handle. Try again ...");
                } catch(Exception ex) {
                    System.err.println("Unknown exception occured. Please, try again ...");
                }
            } while(atm.hasActiveSession());

            try {
                turnOff = terminal.readString("Do you want to leave the program (yes|y)? ");
            } catch(Exception ex) {
                System.err.println("Something went wrong ... quiting.");
                System.exit(2);
            }
        } while(!turnOff.equals("y") && !turnOff.equals("yes"));
    
        terminal.close();      
    }
}