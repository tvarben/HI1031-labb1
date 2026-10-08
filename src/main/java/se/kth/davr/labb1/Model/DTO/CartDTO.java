package se.kth.davr.labb1.Model.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CartDTO {
    private final List<CartItemDTO> items;
    private final long total;
    private final long itemCount;

    public CartDTO(List<CartItemDTO> items) {
        this.items = Collections.unmodifiableList(
                new ArrayList<>(items)
        );
        long total = 0;
        long itemCount = 0;

        for (CartItemDTO item : this.items) {
            total += item.getTotalPrice();
            itemCount += item.getQuantity();
        }
        this.total = total;
        this.itemCount = itemCount;
    }
    public List<CartItemDTO> getItems() {
        return items;
    }
    public long getTotal() {
        return total;
    }
    public long getItemCount() {
        return itemCount;
    }
}