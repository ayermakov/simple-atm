package com.atm;

import java.util.Scanner;
import java.io.*;

public class Terminal {
    private static Scanner scanner;
    private static Console console;

    public Terminal() {
        console = System.console();
        if (console != null) {
            scanner = new Scanner(console.reader());
        } else {
            System.err.println("No console.");
            System.exit(1);
        }
    }

    public static String readString(String prompt) throws Exception {
        print(prompt);
        return readString();
    }

    public static String readString() throws Exception {
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

    public static void println() {
        System.out.println();
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void close() {
        scanner.close();
    }
}