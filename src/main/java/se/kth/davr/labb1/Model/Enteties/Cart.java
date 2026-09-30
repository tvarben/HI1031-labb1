package se.kth.davr.labb1.Model.Enteties;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<OrderedItem> items = new ArrayList<>();

    public void addItem(OrderedItem item) {
        items.add(item);
    }
    public List<OrderedItem> getItems() {
        return items;
    }
    public int getTotal() {
        int sum = 0;
        for(OrderedItem item : items) {
            sum += item.calculateTotalPrice();
        }
        return sum;
    }
    public int getItemCount() {
        return items.size();
    }

}
