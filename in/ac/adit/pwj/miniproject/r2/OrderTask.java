package in.ac.adit.pwj.miniproject.r2;

import java.util.List;
import java.util.Map;

class OrderTask implements Runnable {

    private OrderManager manager;
    private int orderId;
    private List<String> items;

    public OrderTask(OrderManager manager, int orderId, List<String> items) {
        this.manager = manager;
        this.orderId = orderId;
        this.items = items;
    }

    @Override
    public void run() {
        try {
            manager.placeOrder(orderId, items);
            manager.generateBill(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PaymentTask implements Runnable {

    private Map<Integer, Double> payments;
    private int orderId;
    private double amount;

    public PaymentTask(Map<Integer, Double> payments, int orderId, double amount) {
        this.payments = payments;
        this.orderId = orderId;
        this.amount = amount;
    }

    @Override
    public void run() {
        payments.put(orderId, amount);
        System.out.println("Payment successful for Order ID: " + orderId);
    }
}
