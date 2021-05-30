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

    public static User validateUser(String password, String userName) throws SQLException {
        User result = new User(password, userName);
        System.out.println();
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
}
