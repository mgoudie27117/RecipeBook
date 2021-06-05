package com.sweng.recipebook.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - Database connection constant class.
 */
public final class DatabaseConnection {
    private static Connection connection = null;

    static {
        String url = "jdbc:oracle:thin:SYSTEM/SWENG894@localhost:1521/XE";
        String user = "SYSTEM";
        String password = "SWENG894";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * getConnection - Static method to return the application database connection.
     * 
     * @return - Connection object with application database.
     */
    public static Connection getConnection() {
        return connection;
    }
}
