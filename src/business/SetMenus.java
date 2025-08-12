/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;
import model.Setmenu;
import tools.Inputter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.Serializable;
import java.util.List;
import tools.StringUtils;

/**
 *
 * @author thanh
 */
public class SetMenus extends ArrayList<Setmenu> implements Workable<Setmenu> {
    private Inputter inputter = new Inputter();
    private static final String CSV_FILE = "feastMenu.csv";

    public SetMenus() {
        loadFromCSV();
    }

    @Override
    public void addNew(Setmenu menu) {
        if (validate(menu)) {
            this.add(menu);
            System.out.println("Menu added successfully!");
        }
    }

    @Override
    public void update(Setmenu menu) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCode().equalsIgnoreCase(menu.getCode())) {
                this.set(i, menu);
                System.out.println("Menu updated successfully!");
                return;
            }
        }
        System.out.println("Menu not found!");
    }

    @Override
    public Setmenu search(String code) {
        for (Setmenu menu : this) {
            if (menu.getCode().equalsIgnoreCase(code)) {
                return menu;
            }
        }
        return null;
    }

    @Override
    public void display() {
        if (this.isEmpty()) {
            System.out.println("No feast menus available.");
            return;
        }

        // Sort by price in ascending order
        this.sort(Comparator.comparing(Setmenu::getPrice));
        System.out.println("\n=== FEAST MENU LIST ===");
        System.out.println("Sorted by price in ascending order:");
        System.out.printf("%-8s | %-25s | %-12s | %s\n", "Code", "Menu Name", "Price (VND)", "Ingredients");
        System.out.println(StringUtils.repeat("─", 100));
        for (Setmenu menu : this) {
            System.out.println(menu.toString());
        }
        System.out.println(StringUtils.repeat("─", 100));
        System.out.println("Total menus: " + this.size());
    }

    @Override
    public boolean validate(Setmenu menu) {
        if (menu == null) return false;
        if (menu.getCode() == null || menu.getCode().trim().isEmpty()) return false;
        if (menu.getName() == null || menu.getName().trim().isEmpty()) return false;
        if (menu.getPrice() <= 0) return false;
        return true;
    }

    /**
     * Load feast menus from CSV file
     */
    public void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.out.println("Cannot read data from \"" + CSV_FILE + "\". Please check it.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                Setmenu menu = parseCSVLine(line);
                if (menu != null) {
                    this.add(menu);
                }
            }
            System.out.println("Successfully loaded " + this.size() + " feast menus from " + CSV_FILE);
        } catch (IOException e) {
            System.out.println("Error reading " + CSV_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Parse a CSV line into a SetMenu object
     */
    private Setmenu parseCSVLine(String line) {
        try {
            // Handle quoted fields containing commas
            List<String> fields = new ArrayList<>();
            boolean inQuotes = false;
            StringBuilder currentField = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    fields.add(currentField.toString().trim());
                    currentField = new StringBuilder();
                } else {
                    currentField.append(c);
                }
            }
            fields.add(currentField.toString().trim()); // Add the last field

            if (fields.size() >= 4) {
                String code = fields.get(0);
                String name = fields.get(1);
                double price = Double.parseDouble(fields.get(2));
                String ingredients = fields.get(3);
                return new Setmenu(code, name, price, ingredients);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing line: " + line);
        }
        return null;
    }

    /**
     * Search menus by name (partial match)
     */
    public List<Setmenu> searchByName(String keyword) {
        List<Setmenu> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Setmenu menu : this) {
            if (menu.getName().toLowerCase().contains(lowerKeyword)) {
                results.add(menu);
            }
        }
        return results;
    }

    /**
     * Get menu by code
     */
    public Setmenu getMenuByCode(String code) {
        return search(code);
    }

    /**
     * Check if menu code exists
     */
    public boolean menuExists(String code) {
        return search(code) != null;
    }

    /**
     * Get all available menu codes
     */
    public List<String> getAllMenuCodes() {
        List<String> codes = new ArrayList<>();
        for (Setmenu menu : this) {
            codes.add(menu.getCode());
        }
        return codes;
    }

    /**
     * Display menus in a simple format for selection
     */
    public void displayForSelection() {
        if (this.isEmpty()) {
            System.out.println("No feast menus available.");
            return;
        }

        System.out.println("\n=== AVAILABLE FEAST MENUS ===");
        System.out.printf("%-8s | %-25s | %12s\n", "Code", "Menu Name", "Price (VND)");
        System.out.println(StringUtils.repeat("─", 50));
        for (Setmenu menu : this) {
            System.out.printf("%-8s | %-25s | %,12.0f\n",
                    menu.getCode(), menu.getName(), menu.getPrice());
        }
        System.out.println(StringUtils.repeat("─", 50));
    }
}
