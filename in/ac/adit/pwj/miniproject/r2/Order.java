package in.ac.adit.pwj.miniproject.r2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Base class for Order
public abstract class Order{
    protected int orderId;
    protected List<String> items;
    protected double totalAmount;

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public abstract void placeOrder(List<String> items, Map<String, Double> menu);

    public double getTotalAmount() {
        return totalAmount;
    }
}

// DineIn Order
class DineInOrder extends Order {
    public DineInOrder(int orderId) {
        super(orderId);
    }

    @Override
    public void placeOrder(List<String> items, Map<String, Double> menu) {
        this.items.addAll(items);
        for (String item : items) {
            totalAmount += menu.get(item);
        }
        System.out.println("Dine-In Order Placed: " + items);
    }
}

// TakeAway Order
class TakeAwayOrder extends Order {
    public TakeAwayOrder(int orderId) {
        super(orderId);
    }

    @Override
    public void placeOrder(List<String> items, Map<String, Double> menu) {
        this.items.addAll(items);
        for (String item : items) {
            totalAmount += menu.get(item);
        }
        System.out.println("TakeAway Order Placed: " + items);
    }
}
