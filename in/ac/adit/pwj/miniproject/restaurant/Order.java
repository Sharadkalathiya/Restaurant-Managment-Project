package in.ac.adit.pwj.miniproject.restaurant;
import java.util.ArrayList;
import java.util.List;

abstract class Order {
    protected String orderId;
    protected List<String> items;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
    }

    public abstract String getOrderType();

    public String getOrderId() {
        return orderId;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }
}

class DineInOrder extends Order {
    public DineInOrder(String orderId) {
        super(orderId);
    }

    @Override
    public String getOrderType() {
        return "DineIn";
    }
}

class TakeAwayOrder extends Order {
    public TakeAwayOrder(String orderId) {
        super(orderId);
    }

    @Override
    public String getOrderType() {
        return "TakeAway";
    }
}
