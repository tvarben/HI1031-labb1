package se.kth.davr.labb1.Model.DTO;

import se.kth.davr.labb1.Model.Enteties.Item;

public final class ItemDTO {

    private final int id;
    private final String name;
    private final int price;
    private final int quantity;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}