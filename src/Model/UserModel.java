package Model;
import DAO.*;

public class UserModel {
    private UserDAOimplement dao;

    public UserModel(UserDAOimplement dao) {
        this.dao = dao;
    }

    public boolean addUser(String username, String password) {
        if (dao.usernameExists(username)) {
            System.err.println("The username is already taken. Please choose a different one.");
            return false;
        }

        if (password.length() != 8) {
            System.err.println("Password must be exactly 8 characters long.");
            return false;
        }

        User user = new User(username, password);
        dao.addUser(user);
        System.out.println("User successfully added.");
        return true;
    }

    public boolean checkUser(String username, String password) {
        if (password.length() != 8) {
            System.err.println("Password must be exactly 8 characters long.");
            return false;
        }

        User user = new User(username, password);
        return dao.checkUser(user);
    }

    public int getUserIds(String username, String password) throws Exception {
        int id = dao.getUsersIds(username, password);
        if (id == 0) throw new Exception("Invalid credentials. User ID not found.");
        return id;
    }
}