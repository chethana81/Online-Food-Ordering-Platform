/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinefoodorderingplatform;

/**
 *
 * @author DINIDU
 */
public class MenuItem {
    private String name;
    private double price;
    private String category;
    
    public MenuItem(String name, double price, String category){
        setName(name);
        setPrice(price);
        setCategory(category);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return name + " (" + category + ") - Rs. " + price;
    }
}
