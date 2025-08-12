package tools;


import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thanh
 */
public class Inputter {
      private Scanner scanner;
    
    public Inputter() {
        this.scanner = new Scanner(System.in);
    }
    
    public Inputter(Scanner scanner) {
        this.scanner = scanner;
    }
    
    // Get string input with message
    public String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    // Get integer input with validation 
    public int getInt(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
                return Integer.parseInt(temp);
            }
            System.out.println("Invalid integer! Please re-enter...");
        }
    }
    
    // Get positive integer input 
    public int getPositiveInt(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.POSITIVE_INT_VALID)) {
                return Integer.parseInt(temp);
            }
            System.out.println("Invalid positive integer! Please re-enter...");
        }
    }
    
    // Get double input with validation  
    public double getDouble(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
                return Double.parseDouble(temp);
            }
            System.out.println("Invalid number! Please re-enter...");
        }
    }
    
    // Get positive double input 
    public double getPositiveDouble(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.POSITIVE_DOUBLE_VALID)) {
                return Double.parseDouble(temp);
            }
            System.out.println("Invalid positive number! Please re-enter...");
        }
    }
    
    // Generic input with pattern validation and loop option
    public String inputAndLoop(String message, String pattern, boolean isLoop) {
        String result = "";
        boolean more = true;
        do {
            result = getString(message);
            more = !Acceptable.isValid(result, pattern);
            if (more && isLoop) {
                System.out.println("Data is invalid! Re-enter...");
            }
        } while (isLoop && more);
        return result.trim();
    }
    
    // Simplified version without loop parameter (always loops on invalid input)
    public String inputAndLoop(String message, String pattern) {
        return inputAndLoop(message, pattern, true);
    }
    
    // Get date input with validation
    public String getDate(String message) {
        return inputAndLoop(message + " (dd/MM/yyyy): ", Acceptable.DATE_VALID);
    }
    
    // Get customer ID input
    public String getCustomerId(String message) {
        return inputAndLoop(message, Acceptable.CUS_ID_VALID);
    }
    
    // Get menu code input
    public String getMenuCode(String message) {
        return inputAndLoop(message, Acceptable.MENU_CODE_VALID);
    }
    
    // Get name input
    public String getName(String message) {
        return inputAndLoop(message, Acceptable.NAME_VALID);
    }
    
    // Get phone input
    public String getPhone(String message) {
        return inputAndLoop(message, Acceptable.PHONE_VALID);
    }
    
    // Get email input
    public String getEmail(String message) {
        return inputAndLoop(message, Acceptable.EMAIL_VALID);
    }
}