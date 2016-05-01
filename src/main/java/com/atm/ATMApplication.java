package com.atm;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class ATMApplication {
    private static Scanner scanner;
    private static Console console;

    private static String readString(String prompt) throws Exception {
        print(prompt);
        return readString();
    }

    private static String readString() throws Exception {
        String line = "";
        
        do {
            if(scanner.hasNextLine()) {
                line = scanner.nextLine();
                line = line.trim();
            } else {
                throw new Exception("Unknown IO exception.");
            }
        } while(line.equals(""));

        return line;
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private static void print(String s) {
        System.out.print(s);
    }

    public static void main(String[] args) {
        console = System.console();
        if (console != null) {
            scanner = new Scanner(console.reader());
        } else {
            System.err.println("No console.");
            System.exit(1);
        }

        print("Welcome! Simple session-based ATM. Set of your accounts to play around:");        
        
        Account firstAccount = new Account("44445555", "6666", 233);
        Account secondAccount = new Account("11112222", "3333", 478);
        Bank bank = new Bank(firstAccount, secondAccount);

        println("\n\tAttention! All data will be lost when you cancel the program execution.\n");
        
        String turnOff = "";
        do {
            String number = "", pin = "";
            boolean accepted;
            do {    
                accepted = false;
                try {
                    number = readString("Enter card number: ");
                    pin = readString("Enter pin: ");

                    accepted = bank.login(number, pin);

                    if(accepted) {
                        println("Success!");
                    } else{
                        println("Wrong card number or pin! Try again ...");
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                    System.err.println("Input/output exception raised, try again ...");
                }
            } while(!accepted); 

            println("Choose action:\n\t[1] Show my account balance.\n\t[2] Withdraw money.\n\t[3] Put money into account.\n\t[4] Transfer.\n\t[5] Logout.");
            
            int actionChoice = 0;
            do {
                try {
                    String temp = readString("Which action to do next (1-5): ");
                    actionChoice = Integer.parseInt(temp);

                    switch(actionChoice) {
                        case 1:
                            break; 
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4: 
                            break;
                        case 5:
                            System.out.println("Your session is going to close ..."); 
                            break;
                        default:
                            System.err.println("Wrong choice ... try again from 1 to 5.");
                    }
                } catch(InputMismatchException ime) {
                    System.err.println("Input mismatch exception handle. Try again ...");
                } catch(Exception ex) {
                    System.err.println("Unknown exception occured. Please, try again ...");
                }
            } while(actionChoice != 5);

            try {
                turnOff = readString("Do you want to leave the program (yes|y)? ");
            } catch(Exception ex) {
                System.err.println("Something went wrong ... quiting.");
                System.exit(2);
            }
        } while(!turnOff.equals("y") && !turnOff.equals("yes"));
    
        scanner.close();        
    }
}