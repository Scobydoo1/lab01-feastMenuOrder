/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package business;

/**
 *
 * @author thanh
 */
public interface Workable<T> {

    public void addNew(T item);

    public void update(T item);

    public T search(String criteria);

    public void display();

    public boolean validate(T item);
}
