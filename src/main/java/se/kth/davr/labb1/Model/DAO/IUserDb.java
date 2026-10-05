package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;
import java.util.List;
import se.kth.davr.labb1.Model.Enteties.Role;
import se.kth.davr.labb1.Model.Exceptions.UpdateException;

/**
 * This interface declares methods for querying User
 */
public interface IUserDb {

    //returns null if user is not found
    User findUserById(int id) throws SelectException;

    //returns null is user is not found
    User findUserByUsername(String username) throws SelectException;

    List<User> getAllUsers() throws SelectException;

    void updateUserRole(int userId, Role role) throws UpdateException;
}
