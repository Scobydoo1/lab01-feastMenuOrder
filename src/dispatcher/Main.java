/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import business.Customers;
import business.orders;
import business.setMenus;
import tools.Inputter;
import tools.StringUtils;

/**
 *
 * @author thanh
 */
 public class Main {

    private static Customers customerManager = new Customers();
    private static setMenus menuManager = new setMenus();
    private static orders orderManager = new orders(customerManager, menuManager);
    private static Inputter inputter = new Inputter();
    
    public static void main(String[] args) {
        System.out.println(StringUtils.repeat("=", 60));
        System.out.println("         TRADITIONAL FEAST ORDER MANAGEMENT SYSTEM");
        System.out.println(StringUtils.repeat("=", 60));
        System.out.println("System initialized successfully!");
        System.out.println("Data loaded from files.");
        showMainMenu();
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n" + StringUtils.repeat("=", 50));
            System.out.println("         TRADITIONAL FEAST MANAGEMENT");
            System.out.println(StringUtils.repeat("=", 50));
            System.out.println("1. Register customers");
            System.out.println("2. Update customer information");
            System.out.println("3. Search for customer information by name");
            System.out.println("4. Display feast menus");
            System.out.println("5. Place a feast order");
            System.out.println("6. Update order information");
            System.out.println("7. Save data to file");
            System.out.println("8. Display Customer or Order lists");
            System.out.println("9. Quit");
            System.out.println(StringUtils.repeat("=", 50));
            
            int choice = inputter.getInt("Enter your choice (1-9): ");
            
            switch (choice) {
                case 1:
                    registerCustomers();
                    break;
                case 2:
                    updateCustomerInformation();
                    break;
                case 3:
                    searchCustomerByName();
                    break;
                case 4:
                    displayFeastMenus();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    updateOrderInformation();
                    break;
                case 7:
                    saveDataToFile();
                    break;
                case 8:
                    displayLists();
                    break;
                case 9:
                    quitApplication();
                    return;
                default:
                    System.out.println("Invalid choice! Please enter 1-9.");
            }
        }
    }
    
    private static void registerCustomers() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         CUSTOMER REGISTRATION");
        System.out.println(StringUtils.repeat("=", 50));
        customerManager.registerCustomer();
    }
    
    private static void updateCustomerInformation() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         UPDATE CUSTOMER INFORMATION");
        System.out.println(StringUtils.repeat("=", 50));
        customerManager.updateCustomer();
    }
    
    private static void searchCustomerByName() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         SEARCH CUSTOMERS");
        System.out.println(StringUtils.repeat("=", 50));
        customerManager.searchCustomerByName();
    }
    
    private static void displayFeastMenus() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         FEAST MENUS");
        System.out.println(StringUtils.repeat("=", 50));
        menuManager.display();
    }
    
    private static void placeOrder() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         PLACE FEAST ORDER");
        System.out.println(StringUtils.repeat("=", 50));
        orderManager.placeOrder();
    }
    
    private static void updateOrderInformation() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         UPDATE ORDER INFORMATION");
        System.out.println(StringUtils.repeat("=", 50));
        orderManager.updateOrder();
    }
    
    private static void saveDataToFile() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         SAVE DATA");
        System.out.println(StringUtils.repeat("=", 50));
        System.out.println("Choose data to save:");
        System.out.println("1. Save customer data");
        System.out.println("2. Save order data");
        System.out.println("3. Save both");
        
        int choice = inputter.getInt("Enter your choice (1-3): ");
        
        switch (choice) {
            case 1:
                customerManager.saveToFile();
                break;
            case 2:
                orderManager.saveToFile();
                break;
            case 3:
                customerManager.saveToFile();
                orderManager.saveToFile();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void displayLists() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         DISPLAY LISTS");
        System.out.println(StringUtils.repeat("=", 50));
        System.out.println("Choose what to display:");
        System.out.println("1. Display all customers");
        System.out.println("2. Display all orders");
        System.out.println("3. Display feast menus");
        System.out.println("4. Display system statistics");
        
        int choice = inputter.getInt("Enter your choice (1-4): ");
        
        switch (choice) {
            case 1:
                displayCustomerList();
                break;
            case 2:
                displayOrderList();
                break;
            case 3:
                displayFeastMenus();
                break;
            case 4:
                displaySystemStatistics();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void displayCustomerList() {
        if (customerManager.isEmpty()) {
            System.out.println("Does not have any customer information.");
        } else {
            customerManager.display();
        }
    }
    
    private static void displayOrderList() {
        if (orderManager.isEmpty()) {
            System.out.println("No data in the system.");
        } else {
            orderManager.display();
        }
    }
    
    private static void displaySystemStatistics() {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        System.out.println("Total customers: " + customerManager.size());
        System.out.println("Total orders: " + orderManager.size());
        System.out.println("Available feast menus: " + menuManager.size());
        
        if (!orderManager.isEmpty()) {
            // Calculate total revenue
            double totalRevenue = 0;
            for (Object orderObj : orderManager) {
                if (orderObj instanceof model.Order) {
                    model.Order order = (model.Order) orderObj;
                    totalRevenue += order.getTotalCost();
                }
            }
            System.out.println("Total revenue: " + String.format("%,.0f", totalRevenue) + " VND");
        }
        
        System.out.println("\nData files status:");
        System.out.println("- customers.dat: " + (new java.io.File("customers.dat").exists() ? "Available" : "Not found"));
        System.out.println("- feast_order_service.dat: " + (new java.io.File("feast_order_service.dat").exists() ? "Available" : "Not found"));
        System.out.println("- feastMenu.csv: " + (new java.io.File("feastMenu.csv").exists() ? "Available" : "Not found"));
    }
    
    private static void quitApplication() {
        System.out.println("\n" + StringUtils.repeat("=", 50));
        System.out.println("         QUIT APPLICATION");
        System.out.println(StringUtils.repeat("=", 50));
        
        String saveChoice = inputter.getString("Do you want to save data before quitting? (Y/N): ");
        if (saveChoice.equalsIgnoreCase("Y")) {
            customerManager.saveToFile();
            orderManager.saveToFile();
        }
        System.out.println("\nThank you for using Traditional Feast Order Management System!");
        System.out.println("System shutting down...");
        System.out.println(StringUtils.repeat("=", 60));
        inputter = null;
        System.exit(0);
    }
    
    private static void displayWelcomeMessage() {
        System.out.println("\nWelcome to Traditional Feast Order Management System!");
        System.out.println("This system helps you manage:");
        System.out.println("- Customer registration and information");
        System.out.println("- Feast menu catalog from CSV file");
        System.out.println("- Order placement and management");
        System.out.println("- Data persistence with binary files");
        System.out.println("\nFeatures:");
        System.out.println("✓ Comprehensive input validation");
        System.out.println("✓ Duplicate order prevention");
        System.out.println("✓ Automatic data loading and saving");
        System.out.println("✓ Professional reporting and statistics");
    }
}
