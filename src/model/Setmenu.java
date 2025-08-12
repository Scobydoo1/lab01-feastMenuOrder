/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author thanh
 */
public class Setmenu implements Serializable {
     private static final long serialVersionUID = 1L;
    
    private String code;
    private String name;
    private double price;
    private String ingredients;


    public Setmenu() {
    }


    public Setmenu(String code, String name, double price, String ingredients) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-25s | %,12.0f | %s", 
            code, name, price, ingredients.replace("#", " "));
    }

 
    public String toFileFormat() {
        return code + "," + name + "," + price + ",\"" + ingredients + "\"";
    }
}