/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import business.Customers;
import business.Orders;
import business.SetMenus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import models.Customer;
import models.Order;
import tools.Inputter;

/**
 *
 * @author tungi
 */
public class Main {

    private static final int REGISTER_CUSTOMERS = 1;
    private static final int UPDATE_CUSTOMER = 2;
    private static final int SEARCH_CUSTOMER = 3;
    private static final int DISPLAY_MENU = 4;
    private static final int PLACE_ORDER = 5;
    private static final int UPDATE_ORDER = 6;
    private static final int SAVE_DATA = 7;
    private static final int DISPLAY_ALL = 8;
    private static final int EXIT = 9;
    private static final int RETURN_TO_MAIN = 2;
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String ORDERS_FILE = "feast_order_service.dat";
    private static final String MENU_FILE = "FeastMenu.csv";

    private static Inputter inputter;
    private static Customers customers;
    private static Orders orders;
    private static SetMenus setMenus;
    private static Scanner scanner;

    private static void initializeSystem() {
        inputter = new Inputter();
        customers = new Customers(CUSTOMERS_FILE);
        orders = new Orders(ORDERS_FILE);
        setMenus = new SetMenus(MENU_FILE);
        scanner = new Scanner(System.in);
    }

    private static void displayMainMenu() {
        System.out.println("\n----------MAIN MENU------------");
        System.out.println("1. Register customers");
        System.out.println("2. Update customer information");
        System.out.println("3. Seach for customer information by name");
        System.out.println("4. Display feast menu");
        System.out.println("5. Place a feast order");
        System.out.println("6. Update order");
        System.out.println("7. Save data to file");
        System.out.println("8. Display all customers");
        System.out.println("9. Exit");
        System.out.print("Enter Test Case No. : ");
    }

    private static int getMenuChoice() {
        return Integer.parseInt(scanner.nextLine());
    }

    private static void runMainMenu() {
        int testCase;
        do {
            displayMainMenu();
            testCase = getMenuChoice();
            processMenuChoice(testCase);
        } while (testCase != EXIT);
    }

    private static void handleCustomerRegistration() {
        int option;
        do {
            customers.addNew(inputter.inputCustomer(false));
            System.out.println("1. Continue entering new customers");
            System.out.println("2. Return to the main menu");
            System.out.println("Enter your option: ");
            option = Integer.parseInt(scanner.nextLine());
        } while (option != RETURN_TO_MAIN);
    }

    private static void handleCustomerUpdate() {
        int option = 0;
        do {
            System.out.print("Enter customer code: ");
            String customerCode = scanner.nextLine();
            Customer c = customers.searchById(customerCode);
            if (c == null) {
                // Khong ton tai
                System.out.println("This customer does not exist.");
            } else {
                // Co ton tai
                Customer customer = inputter.inputCustomer(true);
                customer.setCustomerCode(c.getCustomerCode());
                customers.update(customer);
            }

            System.out.println("1. Continue edit customers");
            System.out.println("2. Return to the main menu");
            System.out.println("Enter your option: ");
            option = Integer.parseInt(scanner.nextLine());
        } while (option != RETURN_TO_MAIN);
    }

    private static void handleCustomerSearch() {
        String name = "";
        System.out.println("Enter the name or partial name of customers");
        name = scanner.nextLine();
        Set<Customer> set = customers.filterByName(name);
        if (set.size() == 0) {
            System.out.println("No one matches the search criteria!");
        } else {
            customers.show(set);
        }
    }

    private static void handleMenuDisplay() {
        setMenus.readFromFile();
        setMenus.showAll();
    }

    private static void handleOrderPlacement() {
        Order o = inputter.inputOrder(false, customers, setMenus, orders);
        if (orders.isDupplicated(o)) {
            System.out.println("Dupplicate data!");
        } else {
            orders.addNew(o);
            o.display(customers, setMenus);
        }
    }

    private static void handleOrderUpdating() {
        int option = 0;
        do {
            Order o = inputter.inputOrder(true, customers, setMenus, orders);
            if (o != null) {
                orders.update(o);
                o.display(customers, setMenus);
                System.out.println("Update successfull!");
            }
            System.out.println("1. Continue edit orders");
            System.out.println("2. Return to the main menu");
            System.out.println("Enter your option: ");
            option = Integer.parseInt(scanner.nextLine());
        } while (option != RETURN_TO_MAIN);
    }

    private static void handleDataSaving() {
        customers.saveToFile();
        orders.saveToFile();
        System.out.println("The data is successfully saved!");
    }

    private static void handleDisplayAll() {
        HashSet<Customer> c = customers.readFromFile();
        ArrayList<Order> o = orders.readFromFile();
        if (c.size() > 0) {
            customers.show(c);
        } else {
            System.out.println("No customers data!");
        }

        if (o.size() > 0) {
            orders.show(o);
        } else {
            System.out.println("No orders data!");
        }
    }

    private static void processMenuChoice(int testCase) {
        switch (testCase) {
            case REGISTER_CUSTOMERS:
                handleCustomerRegistration();
                break;
            case UPDATE_CUSTOMER:
                handleCustomerUpdate();
                break;
            case SEARCH_CUSTOMER:
                handleCustomerSearch();
                break;
            case DISPLAY_MENU:
                handleMenuDisplay();
                break;
            case PLACE_ORDER:
                handleOrderPlacement();
                break;
            case UPDATE_ORDER:
                handleOrderUpdating();
                break;
            case SAVE_DATA:
                handleDataSaving();
                break;
            case DISPLAY_ALL:
                handleDisplayAll();
                break;
            case EXIT:
                //handleExit();
                break;
            default:
                throw new AssertionError();
        }
    }

    public static void main(String[] args) {
        initializeSystem();
        runMainMenu();
    }

}
