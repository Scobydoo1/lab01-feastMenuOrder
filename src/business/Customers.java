/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import model.Customer;
import tools.*;
import java.io.*;
import java.util.*;;
/**
 *
 * @author thanh
 */
public class Customers extends ArrayList<Customer> implements Workable<Customer> {
    private Inputter inputter = new Inputter();
    private static final String DATA_FILE = "customers.dat";

    public Customers() {
        loadFromFile();
    }

    @Override
    public void addNew(Customer customer) {
        if (validate(customer)) {
            this.add(customer);
            System.out.println("Customer added successfully!");
        }
    }

    @Override
    public void update(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equalsIgnoreCase(customer.getId())) {
                this.set(i, customer);
                System.out.println("Customer updated successfully!");
                return;
            }
        }
        System.out.println("Customer not found!");
    }

    @Override
    public Customer search(String id) {
        for (Customer customer : this) {
            if (customer.getId().equalsIgnoreCase(id)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void display() {
        if (this.isEmpty()) {
            System.out.println("No customers in the system.");
            return;
        }

        // Sort by name alphabetically
        this.sort(Comparator.comparing(Customer::getName));
        System.out.println("\n=== CUSTOMER LIST ===");
        System.out.printf("%-8s | %-25s | %-12s | %-30s\n", "Code", "Name", "Phone", "Email");
        System.out.println(StringUtils.repeat("─", 80));
        for (Customer customer : this) {
            System.out.printf("%-8s | %-25s | %-12s | %-30s\n",
                    customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail());
        }
        System.out.println(StringUtils.repeat("─", 80));
        System.out.println("Total customers: " + this.size());
    }

    @Override
    public boolean validate(Customer customer) {
        if (customer == null) return false;
        if (customer.getId() == null || !Acceptable.isValid(customer.getId(), Acceptable.CUS_ID_VALID)) return false;
        if (customer.getName() == null || !Acceptable.isValid(customer.getName(), Acceptable.NAME_VALID)) return false;
        if (customer.getPhone() == null || !Acceptable.isValid(customer.getPhone(), Acceptable.PHONE_VALID)) return false;
        if (customer.getEmail() == null || !Acceptable.isValid(customer.getEmail(), Acceptable.EMAIL_VALID)) return false;
        return true;
    }

    /**
     * Register a new customer with input validation
     */
    public void registerCustomer() {
        while (true) {
            System.out.println("\n=== REGISTER NEW CUSTOMER ===");
            String id;
            do {
                id = inputter.getCustomerId("Enter customer code (C/G/Kxxxx): ");
                if (search(id) != null) {
                    System.out.println("Customer code already exists. Please enter a different code.");
                    id = ""; // reset to force re-entry
                }
            } while (id.isEmpty());

            String name = inputter.getName("Enter name (2-25 chars): ");
            String phone = inputter.getPhone("Enter phone (10 digits starting with 0): ");
            String email = inputter.getEmail("Enter email: ");

            Customer newCustomer = new Customer(id, name, phone, email);
            addNew(newCustomer);

            String cont = inputter.getString("Continue registering? (Y/N): ");
            if (!cont.equalsIgnoreCase("Y")) break;
        }
    }

    /**
     * Update customer information
     */
    public void updateCustomer() {
        while (true) {
            System.out.println("\n=== UPDATE CUSTOMER INFORMATION ===");
            String id = inputter.getCustomerId("Enter customer code to update: ");
            Customer customer = search(id);
            if (customer == null) {
                System.out.println("This customer does not exist.");
            } else {
                System.out.println("Current information: " + customer.toString());

                String name = inputter.getString("Enter new name (leave blank to keep old): ");
                if (!name.trim().isEmpty() && Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                    customer.setName(name);
                }

                String phone = inputter.getString("Enter new phone (leave blank to keep old): ");
                if (!phone.trim().isEmpty() && Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                    customer.setPhone(phone);
                }

                String email = inputter.getString("Enter new email (leave blank to keep old): ");
                if (!email.trim().isEmpty() && Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                    customer.setEmail(email);
                }

                System.out.println("Customer updated successfully!");
            }

            String cont = inputter.getString("Continue updating? (Y/N): ");
            if (!cont.equalsIgnoreCase("Y")) break;
        }
    }

    /**
     * Search customers by name
     */
    public void searchCustomerByName() {
        System.out.println("\n=== SEARCH CUSTOMER BY NAME ===");
        String keyword = inputter.getString("Enter name or part of name to search: ").toLowerCase();
        List<Customer> matched = searchByName(keyword);

        if (matched.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            matched.sort(Comparator.comparing(Customer::getName));
            System.out.println("\nMatching Customers: " + keyword);
            System.out.printf("%-8s | %-25s | %-12s | %-30s\n", "Code", "Name", "Phone", "Email");
            System.out.println(StringUtils.repeat("─", 80));
            for (Customer customer : matched) {
                System.out.printf("%-8s | %-25s | %-12s | %-30s\n",
                        customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail());
            }
            System.out.println(StringUtils.repeat("─", 80));
        }
    }

    /**
     * Search customers by name (returns list)
     */
    public List<Customer> searchByName(String keyword) {
        List<Customer> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Customer customer : this) {
            if (customer.getName().toLowerCase().contains(lowerKeyword)) {
                results.add(customer);
            }
        }
        return results;
    }

    /**
     * Check if customer exists
     */
    public boolean customerExists(String id) {
        return search(id) != null;
    }

    /**
     * Save customers to binary file
     */
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("Customer data has been successfully saved to \"" + DATA_FILE + "\".");
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    /**
     * Load customers from binary file
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing customer data found. Starting with empty database.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            List<Customer> loadedCustomers = (List<Customer>) ois.readObject();
            this.clear();
            this.addAll(loadedCustomers);
            System.out.println("Successfully loaded " + this.size() + " customers from " + DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    /**
     * Display customers with custom list
     */
    public void display(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            System.out.println("No customers to display.");
            return;
        }

        System.out.printf("%-8s | %-25s | %-12s | %-30s\n", "Code", "Name", "Phone", "Email");
        System.out.println(StringUtils.repeat("─", 80));
        for (Customer customer : customerList) {
            System.out.printf("%-8s | %-25s | %-12s | %-30s\n",
                    customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail());
        }
        System.out.println(StringUtils.repeat("─", 80));
    }
}