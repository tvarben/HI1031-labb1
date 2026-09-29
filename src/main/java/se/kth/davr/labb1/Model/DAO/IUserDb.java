package se.kth.davr.labb1.Model.DAO;

import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

/**
 * This interface declares methods for querying User
 *
 * LOGIN METHOD MAY BE NECESSARY
 */
public interface IUserDb {

    User findUserById(int id) throws SelectException;

    User findUserByUsername(String username) throws SelectException;
}
