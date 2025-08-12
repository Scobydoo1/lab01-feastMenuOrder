/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author thanh
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String orderId;         // Unique order ID
    private String customerId;      // Customer ID
    private String menuId;          // Menu ID
    private String eventDate;       // Event date (dd/MM/yyyy)
    private int numberOfTables;     // Number of tables
    private double menuPrice;       // Menu price per table
    private double totalCost;       // Total cost (price * tables)
    
    /**
     * Default constructor
     */
    public Order() {
        this.orderId = generateOrderId();
    }
    
    /**
     * Parameterized constructor
     */
    public Order(String customerId, String menuId, String eventDate, int numberOfTables, double menuPrice) {
        this.orderId = generateOrderId();
        this.customerId = customerId;
        this.menuId = menuId;
        this.eventDate = eventDate;
        this.numberOfTables = numberOfTables;
        this.menuPrice = menuPrice;
        calculateTotalCost();
    }
    
    private String generateOrderId() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(now);
    }
    
    public void calculateTotalCost() {
        this.totalCost = this.menuPrice * this.numberOfTables;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getMenuId() {
        return menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    public String getEventDate() {
        return eventDate;
    }
    
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    
    public int getNumberOfTables() {
        return numberOfTables;
    }
    
    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
        calculateTotalCost(); // Recalculate total cost
    }
    
    public double getMenuPrice() {
        return menuPrice;
    }
    
    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
        calculateTotalCost(); // Recalculate total cost
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return Objects.equals(customerId, order.customerId) &&
               Objects.equals(menuId, order.menuId) &&
               Objects.equals(eventDate, order.eventDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(customerId, menuId, eventDate);
    }
    
    @Override
    public String toString() {
        return String.format("%-15s | %-12s | %-10s | %-8s | %,12.0f | %-6d | %,15.0f",
                orderId, eventDate, customerId, menuId, menuPrice, numberOfTables, totalCost);
    }
}