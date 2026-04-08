/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinefoodorderingplatform;

import java.util.*;

/**
 *
 * @author USER
 */
public class OrderManager {

    private LinkedList<MenuItem> menu;                // dynamic menu
    private ArrayDeque<Order> normalOrders;           // FIFO queue
    private PriorityQueue<Order> priorityOrders;      // priority queue
    private Deque<Runnable> undoStack;                // stack for undo actions
    private List<Order> completedOrders;              // record of served orders

    public OrderManager() {
        menu = new LinkedList<>();
        normalOrders = new ArrayDeque<>();
        // Premium customers served first, then by timestamp (earlier first)
        priorityOrders = new PriorityQueue<>((o1, o2) -> {
            if (o1.isPremium() && !o2.isPremium()) {
                return -1;
            }
            if (!o1.isPremium() && o2.isPremium()) {
                return 1;
            }
            return Long.compare(o1.getTimestamp(), o2.getTimestamp());
        });
        undoStack = new ArrayDeque<>();
        completedOrders = new ArrayList<>();
    }

    // ---- MENU MANAGEMENT ----
    public void addMenuItem(MenuItem item) {
        menu.add(item);
        undoStack.push(() -> menu.remove(item)); // undo action
        System.out.println("Added: " + item);
    }

    public void removeMenuItem(String name) {
        MenuItem found = null;
        for (MenuItem m : menu) {
            if (m.getName().equalsIgnoreCase(name)) {
                found = m;
                break;
            }
        }
        if (found != null) {
            final MenuItem toRemove = found;  // make it effectively final
            menu.remove(toRemove);

            // record undo (re-add item if undone)
            undoStack.push(() -> menu.add(toRemove));

            System.out.println("Removed: " + toRemove);
        } else {
            System.out.println("Item not found: " + name);
        }
    }

    public void printMenu() {
        System.out.println("\n--- MENU ---");
        menu.forEach(m -> {
            System.out.println(m);
        });
        System.out.println("-------------\n");
    }

    // ---- ORDER PLACEMENT ----
    public void placeOrder(Order order) {
        if (order.isPremium()) {
            priorityOrders.offer(order); // enqueue to priority queue
            undoStack.push(() -> priorityOrders.remove(order)); // undo: remove if undone
            System.out.println("Placed PREMIUM order: " + order);
        } else {
            normalOrders.offer(order); // enqueue to normal queue
            undoStack.push(() -> normalOrders.remove(order)); // undo: remove if undone
            System.out.println("Placed NORMAL order: " + order);
        }

        System.out.println("Queue sizes -> Normal: " + normalOrders.size()
                + ", Priority: " + priorityOrders.size());
    }

    // ---- SERVE NEXT ORDER ----
    public void serveNextOrder() {
        Order next;
        if (!priorityOrders.isEmpty()) {
            next = priorityOrders.poll(); // highest priority first
        } else if (!normalOrders.isEmpty()) {
            next = normalOrders.poll(); // FIFO
        } else {
            System.out.println("No orders to serve.");
            return;
        }

        completedOrders.add(next);
        System.out.println("Served: " + next);

        // record undo (requeue the served order)
        undoStack.push(() -> {
            completedOrders.remove(next);
            if (next.isPremium()) {
                priorityOrders.offer(next);
            } else {
                normalOrders.offer(next);
            }
        });
    }

    // ---- UNDO LAST ACTION ----
    public void undoLastAction() {
        if (!undoStack.isEmpty()) {
            Runnable lastAction = undoStack.pop();
            lastAction.run();
            System.out.println("Last action undone.");
        } else {
            System.out.println("No actions to undo.");
        }
    }

    // ---- END-OF-DAY REPORT ----
    public void printEndOfDayReport() {
        if (completedOrders.isEmpty()) {
            System.out.println("No completed orders today.");
            return;
        }

        // Make a copy to sort
        List<Order> sorted = new ArrayList<>(completedOrders);
        mergeSort(sorted, 0, sorted.size() - 1);

        System.out.println("\n--- END OF DAY REPORT (Total Bill Descending) ---");
        for (Order o : sorted) {
            System.out.println(o);
            for (OrderItem item : o.getItems()) {
                System.out.println("   " + item);
            }
        }
        System.out.println("---------------------------------------------\n");
    }

    // ---- GET QUEUE SIZES ----
    public int getNormalQueueSize() {
        return normalOrders.size();
    }

    public int getPremiumQueueSize() {
        return priorityOrders.size();
    }
    
    // ---- STABLE MERGE SORT ----
    private void mergeSort(List<Order> list, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(list, left, mid);
        mergeSort(list, mid + 1, right);
        merge(list, left, mid, right);
    }

    private void merge(List<Order> list, int left, int mid, int right) {
        List<Order> temp = new ArrayList<>();
        int i = left, j = mid + 1;

        while (i <= mid && j <= right) {
            Order o1 = list.get(i);
            Order o2 = list.get(j);

            if (o1.getTotalBill() > o2.getTotalBill()) {
                temp.add(o1);
                i++;
            } else if (o1.getTotalBill() < o2.getTotalBill()) {
                temp.add(o2);
                j++;
            } else {
                // equal totals → maintain original order (stable)
                temp.add(o1);
                i++;
            }
        }

        while (i <= mid) {
            temp.add(list.get(i++));
        }
        while (j <= right) {
            temp.add(list.get(j++));
        }

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
}
