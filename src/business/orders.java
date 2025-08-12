/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;
import model.Order;
import model.Customer;
import model.Setmenu;
import tools.*;
import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author thanh
 */
public class orders extends HashSet<Order> implements Workable<Order> {
    private Inputter inputter = new Inputter();
    private static final String DATA_FILE = "feast_order_service.dat";
    private Customers customerManager;
    private setMenus menuManager;

    public orders(Customers customerManager, setMenus menuManager) {
        this.customerManager = customerManager;
        this.menuManager = menuManager;
        loadFromFile();
    }

    @Override
    public void addNew(Order order) {
        if (validate(order)) {
            if (this.contains(order)) {
                System.out.println("Duplicate data!");
            } else {
                this.add(order);
                System.out.println("Order placed successfully!");
            }
        }
    }

    @Override
    public void update(Order order) {
        for (Order existingOrder : this) {
            if (existingOrder.getOrderId().equals(order.getOrderId())) {
                this.remove(existingOrder);
                this.add(order);
                System.out.println("Order updated successfully!");
                return;
            }
        }
        System.out.println("Order not found!");
    }

    @Override
    public Order search(String orderId) {
        for (Order order : this) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void display() {
        if (this.isEmpty()) {
            System.out.println("No orders in the system.");
            return;
        }

        // Convert to list for sorting
        List<Order> orderList = new ArrayList<>(this);
        orderList.sort(Comparator.comparing(Order::getEventDate));
        System.out.println("\n=== ORDER LIST ===");
        System.out.println("Sorted by event date in ascending order:");
        System.out.printf("%-15s | %-12s | %-10s | %-8s | %-12s | %-6s | %-15s\n",
                "Order ID", "Event Date", "Customer", "Menu", "Price", "Tables", "Total Cost");
        System.out.println(StringUtils.repeat("─", 100));
        for (Order order : orderList) {
            System.out.println(order.toString());
        }
        System.out.println(StringUtils.repeat("─", 100));
        System.out.println("Total orders: " + this.size());
    }

    @Override
    public boolean validate(Order order) {
        if (order == null) return false;
        if (order.getCustomerId() == null || order.getCustomerId().trim().isEmpty()) return false;
        if (order.getMenuId() == null || order.getMenuId().trim().isEmpty()) return false;
        if (order.getEventDate() == null || order.getEventDate().trim().isEmpty()) return false;
        if (order.getNumberOfTables() <= 0) return false;
        if (order.getMenuPrice() <= 0) return false;

        // Validate that customer exists
        if (!customerManager.customerExists(order.getCustomerId())) {
            System.out.println("Customer does not exist!");
            return false;
        }

        // Validate that menu exists
        if (!menuManager.menuExists(order.getMenuId())) {
            System.out.println("Menu does not exist!");
            return false;
        }

        // Validate future date
        if (!isFutureDate(order.getEventDate())) {
            System.out.println("Event date must be in the future!");
            return false;
        }

        return true;
    }

    /**
     * Place a new feast order with comprehensive validation
     */
    public void placeOrder() {
        while (true) {
            System.out.println("\n=== PLACE A FEAST ORDER ===");

            // Show available customers
            if (customerManager.isEmpty()) {
                System.out.println("No customers registered. Please register customers first.");
                return;
            }

            // Show available menus
            if (menuManager.isEmpty()) {
                System.out.println("No feast menus available. Please check feastMenu.csv file.");
                return;
            }

            // Get customer ID
            String customerId;
            Customer customer;
            do {
                customerId = inputter.getCustomerId("Enter customer code: ");
                customer = customerManager.search(customerId);
                if (customer == null) {
                    System.out.println("Customer not found. Please enter a valid customer code.");
                }
            } while (customer == null);

            // Show available menus for selection
            menuManager.displayForSelection();

            // Get menu code
            String menuId;
            Setmenu menu;
            do {
                menuId = inputter.getMenuCode("Enter set menu code: ");
                menu = menuManager.search(menuId);
                if (menu == null) {
                    System.out.println("Menu not found. Please enter a valid menu code.");
                }
            } while (menu == null);

            // Get number of tables
            int numberOfTables = inputter.getPositiveInt("Enter number of tables: ");

            // Get event date
            String eventDate;
            do {
                eventDate = inputter.getDate("Enter event date");
                if (!isFutureDate(eventDate)) {
                    System.out.println("Event date must be in the future!");
                    eventDate = "";
                }
            } while (eventDate.isEmpty());

            // Create order
            Order newOrder = new Order(customerId, menuId, eventDate, numberOfTables, menu.getPrice());

            // Check for duplicates and add
            if (this.contains(newOrder)) {
                System.out.println("Duplicate data!");
            } else {
                addNew(newOrder);
                displayOrderDetails(newOrder, customer, menu);
            }

            String cont = inputter.getString("Continue placing orders? (Y/N): ");
            if (!cont.equalsIgnoreCase("Y")) break;
        }
    }

    /**
     * Update order information
     */
    public void updateOrder() {
        while (true) {
            System.out.println("\n=== UPDATE ORDER INFORMATION ===");
            String orderId = inputter.getString("Enter Order ID: ");
            Order order = search(orderId);
            if (order == null) {
                System.out.println("This Order does not exist.");
            } else {
                // Check if order is in the past
                if (!isFutureDate(order.getEventDate())) {
                    System.out.println("Cannot update an order whose event date has passed.");
                } else {
                    System.out.println("Current order information:");
                    Customer customer = customerManager.search(order.getCustomerId());
                    Setmenu menu = menuManager.search(order.getMenuId());
                    displayOrderDetails(order, customer, menu);

                    // Update menu
                    String newMenuId = inputter.getString("Enter new menu code (leave blank to keep old): ");
                    if (!newMenuId.trim().isEmpty()) {
                        Setmenu newMenu = menuManager.search(newMenuId);
                        if (newMenu != null) {
                            order.setMenuId(newMenuId);
                            order.setMenuPrice(newMenu.getPrice());
                        }
                    }

                    // Update number of tables
                    String newTablesStr = inputter.getString("Enter new number of tables (leave blank to keep old): ");
                    if (!newTablesStr.trim().isEmpty()) {
                        try {
                            int newTables = Integer.parseInt(newTablesStr);
                            if (newTables > 0) {
                                order.setNumberOfTables(newTables);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number format.");
                        }
                    }

                    // Update event date
                    String newDate = inputter.getString("Enter new event date (dd/MM/yyyy, leave blank to keep old): ");
                    if (!newDate.trim().isEmpty() && isFutureDate(newDate)) {
                        order.setEventDate(newDate);
                    }

                    // Recalculate total cost
                    order.calculateTotalCost();
                    System.out.println("Order updated successfully!");
                }
            }

            String cont = inputter.getString("Continue updating? (Y/N): ");
            if (!cont.equalsIgnoreCase("Y")) break;
        }
    }

    /**
     * Display detailed order information
     */
    private void displayOrderDetails(Order order, Customer customer, Setmenu menu) {
        System.out.println("\n=== ORDER CONFIRMATION ===");
        System.out.println("Customer order information [Order ID: " + order.getOrderId() + "]");
        System.out.println();
        System.out.println("Customer code: " + customer.getId());
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Phone number: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());
        System.out.println();
        System.out.println("Code of Set Menu: " + menu.getCode());
        System.out.println("Set menu name: " + menu.getName());
        System.out.println("Event date: " + order.getEventDate());
        System.out.println("Number of tables: " + order.getNumberOfTables());
        System.out.println("Price: " + String.format("%,.0f", order.getMenuPrice()) + " VND");
        System.out.println("Ingredients:");
        System.out.println(menu.getIngredients().replace("#", "\n"));
        System.out.println();
        System.out.println("Total cost: " + String.format("%,.0f", order.getTotalCost()) + " VND");
        System.out.println(StringUtils.repeat("=", 50));
    }

    /**
     * Check if date is in the future
     */
    private boolean isFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date eventDate = sdf.parse(dateStr);
            Date currentDate = new Date();
            return eventDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Save orders to binary file
     */
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            List<Order> orderList = new ArrayList<>(this);
            oos.writeObject(orderList);
            System.out.println("Order data has been successfully saved to \"" + DATA_FILE + "\".");
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    /**
     * Load orders from binary file
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing order data found. Starting with empty database.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            List<Order> loadedOrders = (List<Order>) ois.readObject();
            this.clear();
            this.addAll(loadedOrders);
            System.out.println("Successfully loaded " + this.size() + " orders from " + DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    /**
     * Get orders by customer ID
     */
    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : this) {
            if (order.getCustomerId().equalsIgnoreCase(customerId)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }
}