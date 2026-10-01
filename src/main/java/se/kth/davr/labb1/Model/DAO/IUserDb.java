package se.kth.davr.labb1.Model.DAO;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

/**
 * This interface declares methods for querying User
 */
public interface IUserDb {

    //returns null if user is not found
    User findUserById(int id) throws SelectException;

    //returns null is user is not found
    User findUserByUsername(String username) throws SelectException;
}
