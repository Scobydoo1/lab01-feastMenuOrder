/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package business;

/**
 *
 * @author thanh
 */
public interface Workable <T> {
    void addNew(T item);
    void update(T item);
    T search(String criteria);
    void display();
    boolean validate(T item);
}
