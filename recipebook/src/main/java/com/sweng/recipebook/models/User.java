package com.sweng.recipebook.models;

import com.sweng.recipebook.data.UserDataAccess;
import java.sql.SQLException;

public class User implements IUser {
    private String FirstName;
    private String LastName;
    private String Password;
    private int UserId;
    private String UserName;

    public User(String firstName, String lastName, String password, int userId, String userName) {
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        UserId = userId;
        UserName = userName;
    }

    public User(String password, String userName) {
        FirstName = "";
        LastName = "";
        Password = password;
        UserId = 0;
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void login() {
        try {
            User result = UserDataAccess.validateUser(this.Password, this.UserName);
            this.FirstName = result.getFirstName();
            this.LastName = result.getLastName();
            this.UserId = result.getUserId();
            System.out.println("User " + this.UserName + " logged in.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
