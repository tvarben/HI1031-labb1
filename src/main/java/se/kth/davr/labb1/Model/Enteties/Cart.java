package se.kth.davr.labb1.Model.Enteties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    //synchronized if user is on the same session with multiple tabs.
    public synchronized void addItem(Item item, int quantity) {
        if(item == null || quantity <= 0) {
            throw new IllegalArgumentException("Antalet måste vara positivt.");
        }
        for(int i = 0; i < items.size(); i++) {
            CartItem existing = items.get(i);

            if(existing.getItem().getId() == item.getId()) {
                long newQuantity = (long) existing.getQuantity() + quantity;

                if(newQuantity > Integer.MAX_VALUE) {
                    throw new IllegalArgumentException("För stort antal.");
                }
                items.set(i, new CartItem(item, (int) newQuantity));
                return;
            }
        }
        items.add(new CartItem(item, quantity));
    }

    public synchronized List<CartItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public synchronized long getTotal() {
        long total = 0;
        for(CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public synchronized long getItemCount() {
        long count = 0;
        for(CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }
}