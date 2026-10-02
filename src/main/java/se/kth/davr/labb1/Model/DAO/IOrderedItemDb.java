package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.OrderedItem;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface declares methods for querying OrderedItems
 */
public interface IOrderedItemDb {

    OrderedItem findOrderedItemById(int id) throws SQLException;

    List<OrderedItem> findOrderedItemByOrderId(int id) throws SQLException;

    List<OrderedItem> findOrderedItemByItemId(int id) throws SQLException;
}
