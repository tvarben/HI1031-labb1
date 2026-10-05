package se.kth.davr.labb1.Model.DAO;

import se.kth.davr.labb1.Model.DBManager;
import se.kth.davr.labb1.Model.Enteties.Item;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDbImpl implements IItemDb {

    @Override
    public Item findItemById(int id) throws SelectException {
        String sql = "SELECT id, name, price, quantity FROM Item WHERE id = ?";

        try(Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? readItem(rs) : null;
            }
        } catch (SQLException e) {
            throw new SelectException("Kunde inte hämta produkten", e);
        }
    }

    @Override
    public List<Item> getAllItems() throws SelectException {
        String sql = "SELECT id, name, price, quantity FROM Item ORDER BY name";
        List<Item> items = new ArrayList<>();

        try(Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                items.add(readItem(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new SelectException("Kunde inte hämta produkterna", e);
        }
    }

    private Item readItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("quantity")
        );
    }
}