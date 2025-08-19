/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import business.Customers;
import business.Orders;
import business.SetMenus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import models.Customer;
import models.Order;
import models.SetMenu;

/**
 *
 * @author tungi
 */
public class Inputter {

    private Scanner scanner;

    public Inputter() {
        scanner = new Scanner(System.in);
    }

    public String input(String message, String errorMsg, String regex) {
        String result;
        boolean reInput = false;
        do {
            System.out.println(message);
            result = scanner.nextLine();
            reInput = !Acceptable.isValid(result, regex);
            if (reInput) {
                System.out.println(errorMsg + ". Plese re-enter ... ");
            }
        } while (reInput);
        return result;
    }

    public Customer inputCustomer(boolean isUpdated) {
        Customer customer = new Customer();

        // Input customerCode
        String message = "Enter Customer code\n (A unique 5-character string. The first character is \"C\", \"G\"or \"K\", followed by 4 digits.)";
        String errorMsg = "Customer code cannot be empty! Customer code must start with C, G, K, followed by 4 digits!";
        String regex = Acceptable.customerCodeRegex;
        if (!isUpdated) {
            customer.setCustomerCode(input(message, errorMsg, regex));
        }
        // Input name
        message = "Input name (A non-empty string between 2 and 25 characters long): ";
        errorMsg = "Error: Name cannot be empty. Name must be between 2 and 25 characters.";
        regex = Acceptable.nameRegex;
        customer.setName(input(message, errorMsg, regex));

        // Input phone
        message = "A 10-digit number belonging to a network operator in Vietnam.";
        errorMsg = "Error: Invalid phone format!";
        regex = Acceptable.phoneRegex;
        customer.setPhoneNumber(input(message, errorMsg, regex));

        // Input email
        message = "Input email: ";
        errorMsg = "Error: Invalid email format!";
        regex = Acceptable.emailRegex;
        customer.setEmail(input(message, errorMsg, regex));

        return customer;
    }

    public Order inputOrder(boolean isUpdated, Customers customers, SetMenus setMenus, Orders orders) {
        // Lấy thông tin order cũ nếu đang update
        Order existingOrder = null;
        String orderId = generateOrderId();

        if (isUpdated) {
            orderId = input("Enter Order Id: ", "Order Id is invalid!", Acceptable.anything);
            existingOrder = orders.searchById(orderId);
            if (existingOrder == null) {
                System.out.println("This order does not exist!");
                return null;
            }
        }

        // Xử lý Customer Code
        String customerCode = isUpdated ? existingOrder.getCustomerCode() : "";
        if (!isUpdated) {
            do {
                String message = "Enter Customer code\n (A unique 5-character string. The first character is \"C\", \"G\"or \"K\", followed by 4 digits.)";
                String errorMsg = "Customer code cannot be empty! Customer code must start with C, G, K, followed by 4 digits!";
                customerCode = input(message, errorMsg, Acceptable.customerCodeRegex);

                if (customers.searchById(customerCode) == null) {
                    System.out.println("Customer is not in the list of Customers. Please re input!");
                }
            } while (customers.searchById(customerCode) == null);
        }

        // Xử lý SetMenu ID
        String setMenuId = "";
        String setMenuMessage = isUpdated
                ? "Enter SetMenu Id (Press Enter to keep current: " + existingOrder.getMenuId() + "):"
                : "Enter SetMenu Id:";
        SetMenu setMenu = null;
        do {
            setMenuId = input(setMenuMessage, "SetMenu is invalid!", Acceptable.anything).trim();

            // Nếu update và không nhập gì, giữ giá trị cũ
            if (isUpdated && setMenuId.isEmpty()) {
                setMenuId = existingOrder.getMenuId();
                break;
            }
            
            setMenu = setMenus.searchById(setMenuId);
            if (setMenu == null) {
                System.out.println("SetMenu is not in the list of SetMenus. Please re input!");
            }
        } while (setMenu == null);
        // Lay gia tien tu menu da chon
        double price = setMenu.getPrice();

        // Xử lý Number of Tables
        int numberOfTables = 0;
        String tableMessage = isUpdated
                ? "Enter number of tables (Press Enter to keep current: " + existingOrder.getNumberOfTables() + "):"
                : "Enter number of tables:";
        String tableErrorMsg = "Number of tables must be greater than zero!";

        while (true) {
            try {
                String tableInput = input(tableMessage, tableErrorMsg, Acceptable.anything).trim();

                // Nếu update và không nhập gì, giữ giá trị cũ
                if (isUpdated && tableInput.isEmpty()) {
                    numberOfTables = existingOrder.getNumberOfTables();
                    break;
                }

                numberOfTables = Integer.parseInt(tableInput);
                if (numberOfTables > 0) {
                    break;
                } else {
                    System.out.println(tableErrorMsg);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter a valid positive integer.");
            }
        }

        // Xử lý Event Date
        Date eventDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateMessage = isUpdated
                ? "Enter preferred event date (dd/MM/yyyy) (Press Enter to keep current: " + sdf.format(existingOrder.getEventDate()) + "):"
                : "Enter preferred event date (dd/MM/yyyy):";

        Date today = new Date();
        boolean validDate = false;

        do {
            try {
                System.out.println(dateMessage);
                String inputDate = scanner.nextLine().trim();

                // Nếu update và không nhập gì, giữ giá trị cũ
                if (isUpdated && inputDate.isEmpty()) {
                    eventDate = existingOrder.getEventDate();
                    validDate = true;
                } else {
                    eventDate = sdf.parse(inputDate);
                    if (eventDate.after(today)) {
                        validDate = true;
                    } else {
                        System.out.println("Error: Event date must be in the future.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid date format! Please use dd/MM/yyyy format.");
            }
        } while (!validDate);

        return new Order(orderId, customerCode, setMenuId, numberOfTables, eventDate, price);
    }

    public String generateOrderId() {
        Date date = new Date();
        //return ""+(date.getYear()+1900)+(date.getMonth()+1)+(date.getDate())+(date.getHours())+(date.getMinutes())+(date.getSeconds());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

}
