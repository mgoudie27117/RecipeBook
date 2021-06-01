package com.sweng.recipebook.models;

import com.sweng.recipebook.data.UserDataAccess;
import java.sql.SQLException;

public class User implements IUser {
    private String firstName;
    private String lastName;
    private String password;
    private int userId;
    private String userName;

    public User(String firstName, String lastName, String password, int userId, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userId = userId;
        this.userName = userName;
    }

    public User(String password, String userName) {
        this.firstName = "";
        this.lastName = "";
        this.password = password;
        this.userId = 0;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void login() {
        try {
            User result = UserDataAccess.validateUser(this.password, this.userName);
            this.firstName = result.getFirstName();
            this.lastName = result.getLastName();
            this.userId = result.getUserId();
            System.out.println("User " + this.userName + " logged in.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
