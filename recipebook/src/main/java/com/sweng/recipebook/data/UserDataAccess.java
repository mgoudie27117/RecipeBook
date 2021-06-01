package com.sweng.recipebook.data;

import com.sweng.recipebook.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component("UserDataAccess")
public class UserDataAccess {
    private static Connection connection = DatabaseConnection.getConnection();

    // NEW
    public static User createUser(String firstName, String lastName, String password, String userName)
            throws SQLException {
        String dml = "INSERT INTO recipebook_user (user_name, password, first_name, last_name) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setString(1, userName);
        statement.setString(2, password);
        statement.setString(3, firstName);
        statement.setString(4, lastName);
        int inserted = statement.executeUpdate();
        if (inserted > 0) {
            return validateUser(password, userName);
        }
        return null;
    }

    // NEW
    public static void removeUser(int userId) throws SQLException {
        String dml = "DELETE FROM recipebook_user WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    public static User validateUser(String password, String userName) throws SQLException {
        User result = new User(password, userName);
        String query = "SELECT user_id, user_name, password, first_name, last_name FROM recipebook_user WHERE user_name = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            return new User(resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getString("password"), resultSet.getInt("user_id"), resultSet.getString("user_name"));
        }
        return result;
    }

    // NEW
    public static Boolean verifyDuplicateUsername(String userName) throws SQLException {
        int result = 0;
        String query = "SELECT user_name FROM recipebook_user WHERE user_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result++;
        }
        return result > 0;
    }
}
