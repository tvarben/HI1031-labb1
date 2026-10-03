package se.kth.davr.labb1.Model.DAO;


import se.kth.davr.labb1.Model.DBManager;
import se.kth.davr.labb1.Model.Enteties.Order;
import se.kth.davr.labb1.Model.Enteties.OrderedItem;
import se.kth.davr.labb1.Model.Exceptions.InsertException;
import se.kth.davr.labb1.Model.Exceptions.InsufficientStockException;

import java.sql.*;
import java.util.List;

public class IOrderDbImpl implements IOrderDb {

    private int addOrder(Connection conn, Order order) throws SQLException {
        String query = "INSERT INTO Orders (user_id, total) VALUES (?,?)";
        try (PreparedStatement stm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, order.getUserId());
            stm.setInt(2, order.getTotal());
            stm.executeUpdate();
            try (ResultSet keys = stm.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new SQLException("no OrderId was generated");
        }
    }

    private void addOrderedItems(Connection conn, int orderId, List<OrderedItem> orderedItems) throws SQLException {
        String query = "INSERT INTO OrderedItem (order_id, item_id, quantity, price) VALUES (?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            for (OrderedItem io : orderedItems) {
                stm.setInt(1, orderId);
                stm.setInt(2, io.getItemId());
                stm.setInt(3, io.getQuantity());
                stm.setInt(4, io.getUnitPrice());
                stm.addBatch();
            }
            stm.executeBatch();
        }
    }

    private void decreaseStock(Connection conn, List<OrderedItem> orderedItems) throws SQLException, InsufficientStockException {
        String query = "UPDATE Item SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            for (OrderedItem oi : orderedItems) {
                stm.setInt(1, oi.getQuantity());
                stm.setInt(2, oi.getItemId());
                stm.setInt(3, oi.getQuantity());
                int rowsChanged = stm.executeUpdate();
                if (rowsChanged == 0) {
                    throw new InsufficientStockException(
                            "Det finns inte tillräckligt många i lager av produkt " + oi.getItemId() + ".");
                }
            }
        }
    }

    @Override
    public int addCompleteOrder(Order order, List<OrderedItem> orderedItems)
            throws InsertException, InsufficientStockException {
        try (Connection conn = DBManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try {
                int orderId = addOrder(conn, order);
                addOrderedItems(conn, orderId, orderedItems);
                decreaseStock(conn, orderedItems);
                conn.commit();
                return orderId;
            } catch (SQLException | InsufficientStockException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new InsertException("Error while placing order", e);
        }
    }
}
