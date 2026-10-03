package se.kth.davr.labb1.Model.Services;

import se.kth.davr.labb1.Model.DAO.IItemDb;
import se.kth.davr.labb1.Model.DAO.IOrderDb;
import se.kth.davr.labb1.Model.DAO.IOrderDbImpl;
import se.kth.davr.labb1.Model.DAO.ItemDbImpl;
import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Enteties.CartItem;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.util.List;

public class OrderService {
    private IOrderDb IOrderDb;
    private IItemDb itemDb;

    public OrderService(IOrderDb IOrderDb, IItemDb IItemDb) {
        this.IOrderDb = IOrderDb;
        this.itemDb = IItemDb;
    }

    public OrderService() {
        this(new IOrderDbImpl(), new ItemDbImpl());
    }

    public void validateStock(Cart cart) throws SelectException, InsufficientStockException {
        List<CartItem> shoppedItems = cart.getItems();
        for (CartItem cartItem : shoppedItems){
            Item listedItem = itemDb.findItemById(cartItem.getItem().getId());
            if (listedItem == null) {
                throw new InsufficientStockException(
                        cartItem.getItem().getName() + " finns inte längre i sortimentet.");
            }
            if(cartItem.getQuantity() > listedItem.getQuantity()) {
                throw new InsufficientStockException("Det finns endast " + listedItem.getQuantity() + " st av " + listedItem.getName() + " i lager.");
            }
        }
    }
}
