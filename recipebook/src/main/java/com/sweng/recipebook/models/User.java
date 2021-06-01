package com.sweng.recipebook.models;

import com.sweng.recipebook.data.UserDataAccess;
import java.sql.SQLException;

public class User implements IUser {
    private Boolean authenticated;
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
        this.authenticated = false;
    }

    public User(String password, String userName) {
        this.firstName = "";
        this.lastName = "";
        this.password = password;
        this.userId = 0;
        this.userName = userName;
        this.authenticated = false;
    }

    public void createUser() {
        try {
            User result = UserDataAccess.createUser(this.firstName, this.lastName, this.password, this.userName);
            if (result != null && result.getUserId() > 0) {
                this.userId = result.getUserId();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean getAuthenticated() {
        return authenticated;
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
            if (result != null && result.getFirstName().length() > 0 && result.getLastName().length() > 0
                    && result.getUserId() > 0) {
                this.authenticated = true;
                this.firstName = result.getFirstName();
                this.lastName = result.getLastName();
                this.userId = result.getUserId();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean verifyDuplicateUsername() {
        Boolean result = false;
        try {
            result = UserDataAccess.verifyDuplicateUsername(this.userName);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
