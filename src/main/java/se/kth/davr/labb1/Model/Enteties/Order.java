package se.kth.davr.labb1.Model.Enteties;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime createdAt;
    private int total;
    private List<OrderedItem> orderedItems;

    public Order(int id, int userId, LocalDateTime createdAt, List<OrderedItem> OrdersList) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.orderedItems = OrdersList; //might change later
        this.total = calculateTotal();
    }

    public Order(int userId, List<OrderedItem> orderedItemList){
        this.id = 0;
        this.userId = userId;
        this.createdAt = null;
        this.orderedItems = new ArrayList<>(orderedItemList);
        this.total = calculateTotal();
    }

    private int calculateTotal() {
        int sum = 0;
        for (OrderedItem o : orderedItems) {
            sum += o.calculateTotalPrice();
        }
        return sum;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Order: [")
                .append("id: ").append(getId())
                .append(", userId: ").append(getUserId())
                .append(", createdAt: ").append(getCreatedAt())
                .append(", total: ").append(getTotal())
                .append(", orderedItems: [");
        for (OrderedItem orderedItem : orderedItems) {
            s.append("\n    ")
                    .append(orderedItem);
        }
        s.append("\n]]");
        return s.toString();
    }

}