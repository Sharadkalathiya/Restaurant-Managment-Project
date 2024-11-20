package in.ac.adit.pwj.miniproject.restaurant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestaurantOrderManagementSystem {

    private OrderManager manager;
    private ExecutorService executor;

    public RestaurantOrderManagementSystem() {
        manager = new OrderManager();
        executor = Executors.newFixedThreadPool(10);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Order");
            System.out.println("2. Remove Order");
            System.out.println("3. Calculate Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Order Type (DineIn/TakeAway): ");
                    String orderType = scanner.nextLine();
                    String line = "";
                    String splitBy = ",";
                    try (BufferedReader br = new BufferedReader(new FileReader("items.csv"))) {
                        while ((line = br.readLine()) != null) {
                            String[] item = line.split(splitBy);
                            System.out.println(item[0] + "  Price: " + item[1]);
                            // menu.put(item[0], Double.valueOf(item[1]));

                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.print("Enter Item: ");
                    String item = scanner.nextLine();
                    executor.submit(() -> {
                        try {
                            Order order = manager.createOrder(orderType);
                            order.addItem(item);
                            manager.saveOrders();
                            System.out.println("Order " + order.getOrderId() + " with item " + item + " added successfully.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    });
                    break;
                case 2:
                    System.out.print("Enter Order ID: ");
                    String orderId = scanner.nextLine();
                    executor.submit(() -> {
                        try {
                            manager.removeOrder(orderId);
                            manager.saveOrders();
                            System.out.println("Order " + orderId + " removed successfully.");
                        } catch (InvalidOrderException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    });
                    break;
                case 3:
                    System.out.print("Enter Order ID: ");
                    orderId = scanner.nextLine();
                    executor.submit(() -> {
                        try {
                            double total = manager.calculateBill(orderId);
                            System.out.println("Total bill for order " + orderId + " is: $" + total);
                        } catch (InvalidOrderException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    });
                    break;
                case 4:
                    scanner.close();
                    executor.shutdown();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        RestaurantOrderManagementSystem system = new RestaurantOrderManagementSystem();
        system.start();
    }
}
