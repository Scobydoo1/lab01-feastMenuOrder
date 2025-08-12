/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;
import java.util.Collections;
/**
 *
 * @author thanh
 */
public class StringUtils {
    /**
     * Repeats a string a specified number of times (JDK 1.8 compatible)
     * Uses Collections.nCopies() for optimal performance
     * 
     * @param str The string to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    public static String repeat(String str, int count) {
        if (str == null) {
            return null;
        }
        if (count <= 0) {
            return "";
        }
        if (count == 1) {
            return str;
        }
        
        // Use Collections.nCopies + String.join for efficiency (JDK 8 compatible)
        return String.join("", Collections.nCopies(count, str));
    }
    
    /**
     * Alternative implementation using StringBuilder for very large strings
     * 
     * @param str The string to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    public static String repeatWithBuilder(String str, int count) {
        if (str == null) {
            return null;
        }
        if (count <= 0) {
            return "";
        }
        if (count == 1) {
            return str;
        }
        
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Creative approach using char array replacement (for educational purposes)
     * 
     * @param str The string to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    public static String repeatWithCharArray(String str, int count) {
        if (str == null) {
            return null;
        }
        if (count <= 0) {
            return "";
        }
        if (count == 1) {
            return str;
        }
        
        char[] chars = new char[str.length() * count];
        for (int i = 0; i < count; i++) {
            str.getChars(0, str.length(), chars, i * str.length());
        }
        return new String(chars);
    }
}