package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.Item;
import java.sql.SQLException;
import java.util.List;

/**
 * This interface declares methods used for querying Items
 *
 * getAllItems will be used for displaying information about all product
 */
public interface IItemDb {

    Item findItemById(int id) throws SQLException;

    List<Item> getAllItems() throws SQLException;

}
