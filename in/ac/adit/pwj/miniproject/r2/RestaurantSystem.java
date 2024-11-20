package in.ac.adit.pwj.miniproject.r2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestaurantSystem {

    public static void main(String[] args) {
        try {
            OrderManager manager = new OrderManager();

            // Create some orders
            int dineInOrderId = manager.createOrder("DineIn");
            int takeAwayOrderId = manager.createOrder("TakeAway");

            // Prepare items for orders
            List<String> dineInItems = Arrays.asList("Pizza", "Fries");
            List<String> takeAwayItems = Arrays.asList("Burger", "Pasta");

            // Manage orders using threads
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(new OrderTask(manager, dineInOrderId, dineInItems));
            executor.execute(new OrderTask(manager, takeAwayOrderId, takeAwayItems));

            // Store payments concurrently
            Map<Integer, Double> payments = new ConcurrentHashMap<>();
            executor.execute(new PaymentTask(payments, dineInOrderId, 15.5));
            executor.execute(new PaymentTask(payments, takeAwayOrderId, 14.0));

            // Wait for all tasks to finish
            executor.shutdown();
            manager.saveOrdersToFile("orders.csv");
            manager.savePaymentsToFile("payments.csv", payments);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
