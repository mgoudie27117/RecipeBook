package com.sweng.recipebook.data;

import com.sweng.recipebook.models.RecipeBookUser;
import com.sweng.recipebook.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * UserDataAccess - Data access class for application users.
 */
@Component("UserDataAccess")
public class UserDataAccess extends DataAccess {

    public UserDataAccess() {
        super();
    }

    /**
     * createUser - Method to insert a user into the application user table.
     * 
     * @param firstName - New user's first name.
     * @param lastName  - New user's last name.
     * @param password  - New user's password.
     * @param userName  - New user's username.
     * @return - A new created User object, null if table insert fails.
     * @throws SQLException
     */
    public User createUser(String firstName, String lastName, String password, String userName, String securityQuestion,
            String securityAnswer) throws SQLException {
        String dml = "INSERT INTO recipebook_user (user_name, password, first_name, last_name, security_question_id, security_answer) VALUES (?, ?, ?, ?, (SELECT security_question_id FROM recipebook_security_questions WHERE security_question = ?), ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setString(1, userName);
        statement.setString(2, password);
        statement.setString(3, firstName);
        statement.setString(4, lastName);
        statement.setString(5, securityQuestion);
        statement.setString(6, securityAnswer);
        statement.executeUpdate();
        return validateUser(password, userName);
    }

    /**
     * getSecurityQuestion - Method to retrieve a user's security question.
     * 
     * @param userName - Username requesting security question.
     * @return - String security question.
     * @throws SQLException
     */
    public String getSecurityQuestion(String userName) throws SQLException {
        String result = "";
        String query = "SELECT security_question FROM recipebook_security_questions WHERE security_question_id = (SELECT security_question_id FROM recipebook_user WHERE user_name = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result = resultSet.getString("security_question");
        }
        return result;
    }

    /**
     * getSecurityQuestions - Method to retrieve all application security questions.
     * 
     * @return - List of security questions.
     * @throws SQLException
     */
    public List<String> getSecurityQuestions() throws SQLException {
        List<String> result = new ArrayList<String>();
        String query = "SELECT security_question FROM recipebook_security_questions";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getString("security_question"));
        }
        return result;
    }

    /**
     * removeUser - Method to remove a user from the application user table.
     * 
     * @param userId - User identification number of user to be removed.
     * @throws SQLException
     */
    public void removeUser(int userId) throws SQLException {
        String dml = "DELETE FROM recipebook_user WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    /**
     * updatePassword - Method to update a user's password.
     * 
     * @param userName    - Username associated with password update.
     * @param newPassword - New password to be updated.
     * @throws SQLException
     */
    public void updatePassword(String userName, String newPassword) throws SQLException {
        String dml = "UPDATE recipebook_user SET password = ? WHERE user_name = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        statement.setString(1, userName);
        statement.setString(2, newPassword);
        statement.executeUpdate();
    }

    /**
     * validateSecurityAnswer - Method to validate the user's security question
     * answer.
     * 
     * @param userName       - Username to validate for.
     * @param securityAnswer - Security answer to be validated.
     * @return - True if valid, otherwise false.
     * @throws SQLException
     */
    public Boolean validateSecurityAnswer(String userName, String securityAnswer) throws SQLException {
        String query = "SELECT user_name FROM recipebook_user WHERE user_name = ? and security_answer = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, securityAnswer);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("user_name").equals(userName);
        } else {
            return false;
        }
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
    public User validateUser(String password, String userName) throws SQLException {
        User result = new RecipeBookUser(password, userName);
        String query = "SELECT user_id, user_name, password, first_name, last_name FROM recipebook_user WHERE user_name = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result = new RecipeBookUser(resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getString("password"), resultSet.getInt("user_id"), resultSet.getString("user_name"));
            result.setAuthenticated(true);
        }
        return result;
    }

    /**
     * verifyDuplicateUsername - Method to verify if the passed username already
     * exists.
     * 
     * @param userName - Username to be verified.
     * @return - True is username exists, otherwise false.
     * @throws SQLException
     */
    public Boolean verifyDuplicateUsername(String userName) throws SQLException {
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
