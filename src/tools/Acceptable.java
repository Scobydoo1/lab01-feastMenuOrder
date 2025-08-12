package tools;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author thanh
 */
public interface Acceptable {
   // Customer ID pattern: C/G/K followed by 4 digits
    public final String CUS_ID_VALID = "^[CGK]\\d{4}$";
    
    // Name pattern: 2-25 characters
    public final String NAME_VALID = "^.{2,25}$";
    
    // Phone pattern: 10 digits starting with 0
    public final String PHONE_VALID = "^0\\d{9}$";
    
    // Email pattern: standard email format
    public final String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    
    // Integer pattern: positive or negative integers 
    public final String INTEGER_VALID = "^-?\\d+$";
    
    // Positive integer pattern: integers greater than 0 
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*$";
    
    // Double pattern: positive or negative decimal numbers 
    public final String DOUBLE_VALID = "^-?\\d+(\\.\\d+)?$";
    
    // Positive double pattern: decimal numbers greater than 0 
    public final String POSITIVE_DOUBLE_VALID = "^([1-9]\\d*(\\.\\d+)?|0\\.\\d*[1-9]\\d*)$";
    
    // Date pattern: dd/MM/yyyy format
    public final String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";
    
    // Menu code pattern: PW followed by 3 digits
    public final String MENU_CODE_VALID = "^PW\\d{3}$";
    
    // Static method to validate data against pattern
    static boolean isValid(String data, String pattern) {
        if (data == null || pattern == null) {
            return false;
        }
        return data.matches(pattern);
    }
}
