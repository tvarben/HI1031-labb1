package se.kth.davr.labb1.Model.Enteties;

public class CartItem {

    private final Item item;
    private final int quantity;

    public CartItem(Item item, int quantity) {
        if(item == null || quantity <= 0) {
            throw new IllegalArgumentException("Ogiltig rad i varukorg");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTotalPrice() {
        return (long) item.getPrice() * quantity;
    }
}