package se.kth.davr.labb1.Model.Services;

import se.kth.davr.labb1.Model.DAO.IItemDb;
import se.kth.davr.labb1.Model.DAO.IOrderDb;
import se.kth.davr.labb1.Model.DAO.IOrderDbImpl;
import se.kth.davr.labb1.Model.DAO.ItemDbImpl;
import se.kth.davr.labb1.Model.Enteties.*;
import se.kth.davr.labb1.Model.Exceptions.InsertException;
import se.kth.davr.labb1.Model.Exceptions.InsufficientStockException;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private IOrderDb iOrderDb;
    private IItemDb itemDb;


    public OrderService(IOrderDb IOrderDb, IItemDb IItemDb) {
        this.iOrderDb = IOrderDb;
        this.itemDb = IItemDb;
    }

    public OrderService() {
        this(new IOrderDbImpl(), new ItemDbImpl());
    }

    public void validateOrder(Cart cart) throws SelectException, InsufficientStockException {
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

    public int confirmOrder(Cart cart, User user) throws InsufficientStockException, InsertException {
        if (user == null || cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Ogiltig order.");
        }
        List<CartItem> shoppedItems = cart.getItems();
        List<OrderedItem> orderedItems = new ArrayList<>();
        for(CartItem ci : shoppedItems){
            Item item = ci.getItem();
            orderedItems.add(new OrderedItem(0, item.getId(), ci.getQuantity(), item.getPrice()));
        }
        Order order = new Order(user.getId(), orderedItems);
        return iOrderDb.addCompleteOrder(order, orderedItems);
    }
}
