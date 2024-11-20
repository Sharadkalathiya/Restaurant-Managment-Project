package in.ac.adit.pwj.miniproject.r2;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class OrderManager {

    private Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private Map<String, Double> menu = new HashMap<>();
    private int orderIdCounter = 1;

    public OrderManager() {
        // Initialize menu items
        menu.put("Pizza", 12.5);
        menu.put("Pasta", 8.0);
        menu.put("Burger", 6.0);
        menu.put("Fries", 3.0);
    }

    public synchronized int createOrder(String orderType) throws Exception {
        Order order;
        if (orderType.equalsIgnoreCase("DineIn")) {
            order = new DineInOrder(orderIdCounter);
        } else if (orderType.equalsIgnoreCase("TakeAway")) {
            order = new TakeAwayOrder(orderIdCounter);
        } else {
            throw new Exception("Invalid Order Type");
        }
        orders.put(orderIdCounter, order);
        return orderIdCounter++;
    }

    public void placeOrder(int orderId, List<String> items) throws Exception {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new Exception("Order ID not found.");
        }
        order.placeOrder(items, menu);
    }

    public void generateBill(int orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            Bill bill = new Bill(order);
            bill.printBill();
        } else {
            System.out.println("Order not found.");
        }
    }

    // Inner Class for Bill Calculation
    private class Bill {

        private Order order;

        public Bill(Order order) {
            this.order = order;
        }

        public void printBill() {
            System.out.println("Bill for Order ID: " + order.orderId);
            System.out.println("Items: " + order.items);
            System.out.println("Total Amount: $" + order.getTotalAmount());
        }
    }

    // Store orders in a CSV file
    public void saveOrdersToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Order order : orders.values()) {
                writer.write(order.orderId + "," + order.items + "," + order.totalAmount + "\n");
            }
        }
    }

    // Store payment records in a CSV file
    public void savePaymentsToFile(String fileName, Map<Integer, Double> payments) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<Integer, Double> entry : payments.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }
}
