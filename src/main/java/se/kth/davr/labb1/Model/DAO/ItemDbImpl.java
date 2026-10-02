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

public class ItemDbImpl implements IItemDb{

    //by putting conn inside try() we make sure that the connection closes when method is done running or error occurs. When stm is closed then rs is also guaranteed to close.
    public Item findItemById(int id) throws SelectException{
        String query = "SELECT * FROM Item where id = ?";
        try(Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement stm = conn.prepareStatement(query)){
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Item result = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("quantity")
                );
                return result;
            }
            return null; //return null if item was not found
        }catch (SQLException e){
            throw new SelectException("Error while searching for item by id",e);
        }
    }

    public List<Item> getAllItems() throws SelectException{
        List<Item> result = new ArrayList<>();
        String query = "SELECT * FROM Item";
        try(Connection conn = DBManager.getInstance().getConnection();
                PreparedStatement stm = conn.prepareStatement(query)){
            ResultSet rs = stm.executeQuery();
            addToResult(result, rs);
            return result;
        }
        catch (SQLException e) {
            throw new SelectException("Error while retrieving all items", e);
        }
    }

    //this could be turned into a static generic <T> util method
    private void addToResult(List<Item> result, ResultSet rs) throws SQLException{
        while(rs.next()){
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("quantity")
            );
            result.add(item);
        }
    }


}
