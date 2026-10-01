package se.kth.davr.labb1.Model.Services;
import se.kth.davr.labb1.Model.DAO.IUserDb;
import se.kth.davr.labb1.Model.DAO.UserDbImpl;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.sql.SQLException;

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
}
