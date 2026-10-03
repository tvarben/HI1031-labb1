package se.kth.davr.labb1.Model.DAO;


import se.kth.davr.labb1.Model.DBManager;
import se.kth.davr.labb1.Model.Enteties.Role;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserDbImpl implements IUserDb{

    @Override
    public User findUserById(int id) throws SelectException {
        String query = "SELECT * FROM `User` WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement stm = conn.prepareStatement(query)){
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User result = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                );
                return result;
            }
            return null; //retun null to indicate nothing found
        } catch (SQLException e) {
            throw new SelectException("Error while searching for User by id", e);
        }
    }

    @Override
    public User findUserByUsername(String username) throws SelectException{
        String query = "SELECT * FROM `User` WHERE username = ?";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement stm = conn.prepareStatement(query)){
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User result = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                );
                return result;
            }
            return null; //retun null to indicate nothing found
        } catch (SQLException e) {
            throw new SelectException("Error while searching for User by Username", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws SelectException {
        String sql = "SELECT id, username, password, role FROM `User` ORDER BY username";
        List<User> users = new ArrayList<>();
        try(Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new SelectException("Kunde inte hämta användarna", e);
        }
    }
}
