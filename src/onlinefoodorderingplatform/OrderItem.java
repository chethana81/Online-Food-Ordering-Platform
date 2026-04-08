/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinefoodorderingplatform;

/**
 *
 * @author USER
 */
public class OrderItem {
    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        setItem(item);
        setQuantity(quantity);
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
    
    @Override
    public String toString() {
        return item.getName() + " x" + quantity + " = Rs. " + getSubtotal();
    }
    
}
