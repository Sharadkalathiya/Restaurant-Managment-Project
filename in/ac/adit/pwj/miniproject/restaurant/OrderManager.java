package in.ac.adit.pwj.miniproject.restaurant;

import java.io.*;
import java.util.*;

class OrderManager {

    private List<Order> orders;
    private Map<String, Double> menu;
    private static final String FILE_NAME = "orders.csv";
    private int nextOrderId;

    public OrderManager() {
        orders = new ArrayList<>();
        menu = new HashMap<>();
        loadOrders();
        loadMenu();
        nextOrderId = orders.size() + 1; // Start from next ID after existing orders
    }

    public Order createOrder(String orderType) {
        String orderId = String.valueOf(nextOrderId++);
        Order order = orderType.equalsIgnoreCase("DineIn") ? new DineInOrder(orderId) : new TakeAwayOrder(orderId);
        orders.add(order);
        saveOrders();
        return order;
    }

    public synchronized void processOrder(String orderId) throws OrderConflictException {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                throw new OrderConflictException("Order already exists!");
            }
        }
        orders.add(new DineInOrder(orderId));
        saveOrders();
    }

    public synchronized void removeOrder(String orderId) throws InvalidOrderException {
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getOrderId().equals(orderId)) {
                iterator.remove();
                saveOrders();
                return;
            }
        }
        throw new InvalidOrderException("Order not found!");
    }

    public synchronized double calculateBill(String orderId) throws InvalidOrderException {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                double total = 0.0;
                for (String item : order.getItems()) {
                    total += menu.getOrDefault(item, 0.0);
                }
                return total;
            }
        }
        throw new InvalidOrderException("Order not found!");
    }

    public void saveOrders() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Order order : orders) {
                String items = String.join(",", order.getItems());
                pw.println(order.getOrderId() + "," + order.getOrderType() + "," + items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String orderId = parts[0];
                String orderType = parts[1];
                List<String> items = Arrays.asList(parts).subList(2, parts.length);

                Order order;
                if (orderType.equals("DineIn")) {
                    order = new DineInOrder(orderId);
                } else {
                    order = new TakeAwayOrder(orderId);
                }
                order.items.addAll(items);
                orders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMenu() {
        // Load your menu items and prices, here is a simple example
        String line = "";
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader("items.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] item = line.split(splitBy);
                menu.put(item[0], Double.valueOf(item[1]));
                
                // System.out.println(menu.equals(item[0]));

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    //     menu.put("Pizza", 10.0);
    //     menu.put("Burger", 5.0);
    //     menu.put("Pasta", 7.0);
    }

    class Bill {

        public double calculateTotal(Order order) {
            double total = 0.0;
            for (String item : order.getItems()) {
                total += menu.getOrDefault(item, 0.0);
            }
            return total;
        }
    }
}
