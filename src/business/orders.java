package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import models.Order;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import models.Customer;
import models.SetMenu;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tungi
 */
public class Orders extends ArrayList<Order> implements Workable<Order> {

    private String pathFile;
    private boolean saved;

    public Orders(String pathFile) {
        this.pathFile = pathFile;
        this.saved = true;
        init();
    }

    private void init() {
        ArrayList<Order> result = readFromFile();
        this.clear();
        this.addAll(result);
    }

    @Override
    public void addNew(Order t) {
        if (isDupplicated(t)) {
            System.out.println("Dupplicate data!");
        } else {
            this.add(t);
            this.saved = false;
        }
    }

    @Override
    public void update(Order t) {
        this.remove(t);
        this.add(t);
        this.saved = false;
    }

    @Override
    public Order searchById(String s) {
        for (Order o : this) {
            if (o.getOrderId().equals(s)) {
                return o;
            }
        }
        return null;
    }

    public void show(ArrayList<Order> list) {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        System.out.println("-------------------------------------------------------------");
        System.out.format("%-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s%n  ", "ID", "Event Date", "Customer ID", "Set Menu", "Price", "Tables", "Cost");
        System.out.println("-------------------------------------------------------------");
        for (Order o : list) {
            System.out.format("%-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s%n  ", o.getOrderId() , formatter.format(o.getEventDate()),o.getCustomerCode(),o.getMenuId(), o.getPrice(), o.getNumberOfTables(), o.getPrice()*o.getPrice());
        }
        System.out.println("-------------------------------------------------------------");
    }

    @Override
    public void showAll() {
    }

    public boolean isDupplicated(Order order) {
        for (Order o : this) {
            if (o.getCustomerCode().equals(order.getCustomerCode())
                    && o.getMenuId().equals(order.getMenuId())
                    && o.getEventDate().equals(order.getEventDate())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSaved() {
        return saved;
    }

    public void saveToFile() {
        if (this.saved) {
            return;
        }
        try {
            // B1. Tao file moi
            File f = new File(pathFile);
            if (!f.exists()) {
                f.createNewFile();
            }

            // B2. Tao FileOutputStream
            FileOutputStream fos = new FileOutputStream(f);

            // B3. Tao ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // B4. Ghi file
            oos.writeObject(this);

            // B5. Dong cac object
            oos.close();

            // B6. Thay doi trang thai cua saved
            this.saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> readFromFile() {
        ArrayList<Order> result = new ArrayList<Order>();
        try {
            // B1. Tao file moi
            File f = new File(pathFile);
            if (!f.exists()) {
                return result;
            }

            // B2. Tao FileInputStream
            FileInputStream fis = new FileInputStream(f);

            // B3. Tao ObjectOutputStream
            ObjectInputStream ois = new ObjectInputStream(fis);

            // B4. Ghi file
            result = (Orders) ois.readObject();

            // B5. Dong cac object
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
