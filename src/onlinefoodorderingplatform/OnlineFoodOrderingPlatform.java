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
public class OnlineFoodOrderingPlatform {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OrderManager manager = new OrderManager();

        // ---- 1. Populate menu ----
        MenuItem burger = new MenuItem("Burger", 350, "Main");
        MenuItem pizza = new MenuItem("Pizza", 800, "Main");
        MenuItem coke = new MenuItem("Coke", 150, "Beverage");
        MenuItem fries = new MenuItem("Fries", 250, "Snack");

        manager.addMenuItem(burger);
        manager.addMenuItem(pizza);
        manager.addMenuItem(coke);
        manager.addMenuItem(fries);

        manager.printMenu();

        // ---- 2. Place orders ----
        System.out.println("\n--- PLACING ORDERS ---");
        Order order1 = new Order("Jenny", false); // normal
        order1.addItem(burger, 2);
        order1.addItem(coke, 1);

        Order order2 = new Order("Ann", true); // premium
        order2.addItem(pizza, 1);
        order2.addItem(fries, 1);

        Order order3 = new Order("Tia", false); // normal
        order3.addItem(fries, 3);

        manager.placeOrder(order1);
        manager.placeOrder(order2);
        manager.placeOrder(order3);
        
        // ---- 3. Serve orders ----
        System.out.println("\n--- SERVING SEQUENCE ---");
        manager.serveNextOrder(); // should serve Bob (premium)
        manager.serveNextOrder(); // should serve Alice (normal)

        // ---- 4. Undo last serve ----
        System.out.println("\n--- UNDO OPERATION ---");
        manager.undoLastAction(); // Alice back into normal queue

        // ---- 5. Serve again ----
        manager.serveNextOrder(); // Alice again
        manager.serveNextOrder(); // Charlie

        // ---- 6. End-of-day report ----
        manager.printEndOfDayReport();
    }
}
