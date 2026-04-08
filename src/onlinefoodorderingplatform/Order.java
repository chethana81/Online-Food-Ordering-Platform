/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinefoodorderingplatform;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class Order {

    private static int nextId = 1; // auto-increment order IDs
    private int orderId;
    private String customerName;
    private boolean premium;
    private List<OrderItem> items;
    private double totalBill;
    private long timestamp; // for stable sorting and priority 

    // Original constructor (empty items list)
    public Order(String customerName, boolean isPremium) {
        this.orderId = nextId++;
        setCustomerName(customerName);
        setIsPremium(isPremium);
        this.items = new ArrayList<>();
        this.timestamp = System.currentTimeMillis();
    }
    
    /*//--- New constructor: accepts a map of MenuItem -> quantity ---
    public Order(String customerName, boolean isPremium, Map<MenuItem, Integer> itemsMap) {
        this(customerName, isPremium); // call original constructor
        for (Map.Entry<MenuItem, Integer> entry : itemsMap.entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            items.add(new OrderItem(item, quantity));
            totalBill += item.getPrice() * quantity;
        }
    }*/
    
    // Existing methods...
    public void addItem(MenuItem item, int quantity) {
        items.add(new OrderItem(item, quantity));
        totalBill += item.getPrice() * quantity;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Order.nextId = nextId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setIsPremium(boolean isPremium) {
        this.premium = isPremium;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "Order #" + orderId + " (" + (premium ? "Premium" : "Normal") + ") - Rs. " 
                + totalBill + " by " + customerName;
    }

   
}
