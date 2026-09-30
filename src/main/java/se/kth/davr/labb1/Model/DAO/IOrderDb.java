package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.Order;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface declares methods for querying Orders
 * addOrder will be the method used for creating a transaction
 */
public interface IOrderDb {

    Order findOrderById(int id) throws SQLException;

    List<Order> findOrdersByUserId(int id) throws SQLException;

    List<Order> findOrdersByDate(LocalDateTime date) throws SQLException;

    void addOrder(Order order) throws SQLException;
}
