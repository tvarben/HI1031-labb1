package se.kth.davr.labb1.Model.Services;

import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

public class CartService {

    private final ItemService itemService;

    public CartService() {
        this.itemService = new ItemService();
    }

    public void addItem(Cart cart, int itemId, int quantity) throws SelectException {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Antalet måste vara minst 1.");
        }
        Item item = itemService.getItemById(itemId);
        cart.addItem(item, quantity);
    }
}