package se.kth.davr.labb1.Model.Services;
import se.kth.davr.labb1.Model.DAO.IUserDb;
import se.kth.davr.labb1.Model.DAO.UserDbImpl;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;
import se.kth.davr.labb1.Model.Enteties.Role;
import se.kth.davr.labb1.Model.Exceptions.UpdateException;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final IUserDb userConn;

    public UserService() throws SQLException {
        this.userConn = new UserDbImpl();
    }

    public User authenticate(String username, String password) throws SelectException {
        User user = userConn.findUserByUsername(username);
        if(user == null) {return null;}
        if (!user.getPassword().equals(password)) {return null;}
        return user;
    }

    public List<User> getAllUsers() throws SelectException {
        return userConn.getAllUsers();
    }

    public User getUserById(int id) throws SelectException {
        return userConn.findUserById(id);
    }
    public void changeUserRole(int adminId, int userId, String roleValue) throws SelectException, UpdateException {

        User admin = userConn.findUserById(adminId); // Kontrollera admin
        if(admin == null || admin.getRole() != Role.admin) {
            throw new SecurityException("Endast admin får ändra användarroller");
        }

        if(userId <= 0) {
            throw new IllegalArgumentException("Ogiltigt användar-ID");
        }
        if(adminId == userId) {
            throw new IllegalArgumentException("Du kan inte ändra din egen roll");
        }

        Role newRole;
        try{
            if(roleValue == null) {
                throw new IllegalArgumentException();
            }
            newRole = Role.valueOf(roleValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ogiltig roll");
        }

        User user = userConn.findUserById(userId);

        if(user == null) {
            throw new IllegalArgumentException("Användaren finns inte");
        }

        if (user.getRole() == newRole) { // Ingen uppdatering om rollen är oförändrad
            return;
        }
        userConn.updateUserRole(userId, newRole);
    }
}
