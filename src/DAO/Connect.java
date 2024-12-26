package DAO;

import java.sql.*;

public class Connect {
    private static Connection connect;
    private static final String url = "jdbc:postgresql://localhost:5432/JavaTp";
    private static final String user = "postgres";
    private static final String password = "Ay19939";


    private Connect() {
    }

    public static Connection getConnection() {
        if (connect == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, password);
                System.out.println("Connection established");
            } catch (Exception e) {
                System.err.println("Couldn't establish the connection: " + e.getMessage());
            }
        }
        return connect;
    }
}