package models;

import business.Customers;
import business.SetMenus;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tungi
 */
public class Order implements Serializable {

    private String orderId;
    private String customerCode;
    private String menuId;
    private int numberOfTables;
    private Date eventDate;
    private double price;

    public Order() {
    }

    public Order(String orderId, String customerCode, String menuId, int numberOfTables, Date eventDate, double price) {
        this.orderId = orderId;
        this.customerCode = customerCode;
        this.menuId = menuId;
        this.numberOfTables = numberOfTables;
        this.eventDate = eventDate;
        this.price = price;
    }

  

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customerCode=" + customerCode + ", menuId=" + menuId + ", numberOfTables=" + numberOfTables + ", eventDate=" + eventDate + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.numberOfTables != other.numberOfTables) {
            return false;
        }
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        if (!Objects.equals(this.customerCode, other.customerCode)) {
            return false;
        }
        if (!Objects.equals(this.menuId, other.menuId)) {
            return false;
        }
        return Objects.equals(this.eventDate, other.eventDate);
    }

    public void display(Customers customers, SetMenus setMenus) {
        System.out.println("-----------------------------");
        System.out.println("Customer order information [Order Id: " + this.getOrderId() + "]");
        System.out.println("-----------------------------");
        Customer c = customers.searchById(this.getCustomerCode());
        c.display();
        System.out.println("-----------------------------");
        SetMenu s = setMenus.searchById(this.getMenuId());
        System.out.format("%-20s: %s\n", "Code of SetMenu", this.getMenuId());
        System.out.format("%-20s: %s\n", "Set menu name", s.getMenuName());
        System.out.format("%-20s: %s\n", "Event date", this.getEventDate());
        System.out.format("%-20s: %s\n", "Number of tables", this.getNumberOfTables());
        System.out.format("%-20s: %s\n", "Price", this.getPrice());
        System.out.println("-----------------------------");
        System.out.format("%-15s:%s\n", "Total cost: ", this.getNumberOfTables() * this.getPrice() + " VND");
    }
}
