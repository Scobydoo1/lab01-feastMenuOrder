package business;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author tungi
 */
public interface Workable<T> {
    public void addNew(T t);
    public void update(T t);
    public T searchById(String id);
    public void showAll();
}
