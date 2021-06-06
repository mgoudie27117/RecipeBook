package com.sweng.recipebook.data;

import com.sweng.recipebook.models.RecipeBookUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * UserDataAccess - Data access class for application users.
 */
@Component("UserDataAccess")
public class UserDataAccess {

    private static Connection connection = DatabaseConnection.getConnection();

    /**
     * createUser - Method to insert a user into the application user table.
     * 
     * @param firstName - New user's first name.
     * @param lastName  - New user's last name.
     * @param password  - New user's password.
     * @param userName  - New user's username.
     * @return - A new created RecipeBookUser object, null if table insert fails.
     * @throws SQLException
     */
    public static RecipeBookUser createUser(String firstName, String lastName, String password, String userName)
            throws SQLException {
        String dml = "INSERT INTO recipebook_user (user_name, password, first_name, last_name) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setString(1, userName);
        statement.setString(2, password);
        statement.setString(3, firstName);
        statement.setString(4, lastName);
        statement.executeUpdate();
        return validateUser(password, userName);
    }

    // NEW
    /**
     * removeUser - Method to remove a user from the application user table.
     * 
     * @param userId - User identification number of user to be removed.
     * @throws SQLException
     */
    public static void removeUser(int userId) throws SQLException {
        String dml = "DELETE FROM recipebook_user WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    /**
     * validateUser - Method to validate the user's username and password in the
     * application user table.
     * 
     * @param password - User's password.
     * @param userName - User's username.
     * @return - A logged in RecipeBookUser object.
     * @throws SQLException
     */
    public static RecipeBookUser validateUser(String password, String userName) throws SQLException {
        RecipeBookUser result = new RecipeBookUser(password, userName);
        String query = "SELECT user_id, user_name, password, first_name, last_name FROM recipebook_user WHERE user_name = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            return new RecipeBookUser(resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getString("password"), resultSet.getInt("user_id"), resultSet.getString("user_name"));
        }
        return result;
    }

    // NEW
    /**
     * verifyDuplicateUsername - Method to verify if the passed username already
     * exists.
     * 
     * @param userName - Username to be verified.
     * @return - True is username exists, otherwise false.
     * @throws SQLException
     */
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
