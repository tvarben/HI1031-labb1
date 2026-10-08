package se.kth.davr.labb1.Model.DTO;

import se.kth.davr.labb1.Model.Enteties.CartItem;

public final class CartItemDTO {

    private final ItemDTO item;
    private final int quantity;
    private final long totalPrice;

    public CartItemDTO(CartItem cartItem) {
        this.item = new ItemDTO(cartItem.getItem());
        this.quantity = cartItem.getQuantity();
        this.totalPrice = cartItem.getTotalPrice();
    }
    public ItemDTO getItem() {
        return item;
    }
    public int getQuantity() {
        return quantity;
    }
    public long getTotalPrice() {
        return totalPrice;
    }
}