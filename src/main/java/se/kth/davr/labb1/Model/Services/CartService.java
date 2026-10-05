package se.kth.davr.labb1.Model.Services;

import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Enteties.CartItem;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

public class CartService {

    private final ItemService itemService;

    public CartService(ItemService itemService) {
        this.itemService = itemService;
    }

    public CartService() {
        this(new ItemService());
    }

    //cart is a session object
    public void addItem(Cart cart, int itemId, int quantity)
            throws SelectException {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Antalet måste vara minst 1.");
        }

        Item item = itemService.getItemById(itemId);

        synchronized (cart) {
            int existingQuantity = 0;

            for(CartItem cartItem : cart.getItems()) {
                if (cartItem.getItem().getId() == itemId) {
                    existingQuantity = cartItem.getQuantity();
                    break;
                }
            }

            long requestedQuantity = (long) existingQuantity + quantity;

            if (requestedQuantity > item.getQuantity()) {
                throw new IllegalArgumentException("Det finns " + item.getQuantity()
                        + " st " + item.getName() + " i lager. Du har redan " + existingQuantity
                        + " st i varukorgen");
            }
            cart.addItem(item, quantity);
        }
    }
    public void removeItem(Cart cart, int itemId) {
        if (itemId <= 0) {
            throw new IllegalArgumentException("Ogiltigt produkt-ID");
        }
        cart.removeItem(itemId);
    }
}