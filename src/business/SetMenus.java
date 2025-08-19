package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import models.SetMenu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tungi
 */
public class SetMenus extends HashMap<String, SetMenu> {

    private String pathFile;
    private boolean saved;

    public SetMenus(String pathFile) {
        this.pathFile = pathFile;
        this.readFromFile();
    }

    public SetMenu dataToObject(String temp) {
        SetMenu setMenu = null;
        try {
            String[] data = temp.split(",");
            if (data[0] != null && !data[0].equals("Code")) {
                setMenu = new SetMenu();
                setMenu.setMenuId(data[0]);
                setMenu.setMenuName(data[1]);
                setMenu.setPrice(Double.valueOf(data[2]));
                setMenu.setIngredients(data[3]);
            }
        } catch (Exception e) {
            setMenu = null;
        }
        return setMenu;
    }

    public void readFromFile() {
        try {
            // B1. Tao doi tuong File
            File f = new File(this.pathFile);
            if (!f.exists()) {
                // Khong ton tai
                System.out.println("File not found!. +\"" + pathFile + "\"");
                return;
            }
            // B2. Tao luong anh xa toi file
            FileReader fr = new FileReader(f);
            // B3. Taoj Buffer de doc du lieu tu File
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            // B4. Doc tung dong du lieu va chuyen doi thanh SetMenu
            while ((temp = br.readLine()) != null) {
                SetMenu setMenu = dataToObject(temp);
                if (setMenu != null) {
                    this.put(setMenu.getMenuId(), setMenu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAll() {
        List<SetMenu> list = new ArrayList<>(this.values());
        Collections.sort(list, new Comparator<SetMenu>() {
            @Override
            public int compare(SetMenu o1, SetMenu o2) {
                return (int) o1.getPrice() - (int) o2.getPrice();
            }
        });

        if (list.size() > 0) {
            System.out.println("------------------------");
            System.out.println("List of Set Menus for ordering party:");
            System.out.println("------------------------");
            for (SetMenu sm : list) {
                System.out.format("%-15s:%s\n", "Code", sm.getMenuId());
                System.out.format("%-15s:%s\n", "Name", sm.getMenuName());
                System.out.format("%-15s:%s\n", "Price", sm.getPrice() + "Vnd");
                System.out.println("Ingredients:");
                System.out.println(sm.getIngredients());
                System.out.println("------------------------");
            }
        }
    }
    public SetMenu searchById(String id){
        return this.get(id);
    }
    
    public boolean contains(String id){
        return this.containsKey(id);
    }
}
