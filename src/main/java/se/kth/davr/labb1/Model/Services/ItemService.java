package se.kth.davr.labb1.Model.Services;

import se.kth.davr.labb1.Model.DAO.IItemDb;
import se.kth.davr.labb1.Model.DAO.ItemDbImpl;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.sql.SQLException;
import java.util.List;

public class ItemService {

    private final IItemDb itemDb; //use interface for low coupeling

    public ItemService(){
        this(new ItemDbImpl());
    }

    public ItemService(IItemDb itemDb) {
        this.itemDb = itemDb;
    }
    public List<Item> getAllItems() throws SelectException {
        return itemDb.getAllItems();
    }
    public Item getItemById(int id) throws SelectException {
        if (id <= 0) {
            throw new IllegalArgumentException("Ogiltigt produkt-ID.");
        }
        Item item = itemDb.findItemById(id);

        if (item == null) {
            throw new IllegalArgumentException("Produkten finns inte.");
        }
        return item;
    }
}