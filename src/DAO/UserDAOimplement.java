package DAO;
import Model.User;
import java.sql.*;

public class UserDAOimplement implements UserDAOI {
    @Override
    public void addUser(User Auser) {
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setString(1, Auser.getUsername());
            st.setString(2, Auser.getPassword());
            st.executeUpdate();
        } catch (SQLException er) {
            System.err.println("Error adding user: " + er.getMessage());
        }
    }

    @Override
    public boolean checkUser(User Auser) {
        String sql = "SELECT username, password FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setString(1, Auser.getUsername());
            st.setString(2, Auser.getPassword());
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException er) {
            System.err.println("Error checking user: " + er.getMessage());
        }
        return false;
    }

    @Override
    public int getUsersIds(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        int id = 0;

        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user ID: " + e.getMessage());
        }

        return id;
    }

    @Override
    public boolean usernameExists(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement st = Connect.getConnection().prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }
        return false;
    }
}