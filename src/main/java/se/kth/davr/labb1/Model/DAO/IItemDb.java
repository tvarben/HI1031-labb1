package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;
import java.util.List;

/**
 * This interface declares methods used for querying Items
 *
 * getAllItems will be used for displaying information about all product
 */
public interface IItemDb {

    Item findItemById(int id) throws SelectException;

    // Return list of all items in shop and empty list if no items exist *
    List<Item> getAllItems() throws SelectException;

}
