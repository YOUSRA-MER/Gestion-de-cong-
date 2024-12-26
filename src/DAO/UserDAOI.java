package DAO;
import Model.*;

public interface UserDAOI {
    void addUser(User usr);
    boolean checkUser(User usr);
    int getUsersIds(String username, String password);
    boolean usernameExists(String username);
}