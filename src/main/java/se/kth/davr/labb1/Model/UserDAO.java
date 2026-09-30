package se.kth.davr.labb1.Model;
import se.kth.davr.labb1.Model.Enteties.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("username"),
                            User.Role.valueOf(rs.getString("role")),
                            rs.getString("password"),
                            rs.getInt("id")
                            );
                }
            }
        }
        return null;
    }
}
