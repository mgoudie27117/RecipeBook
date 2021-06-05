package com.sweng.recipebook.models;

import com.sweng.recipebook.data.UserDataAccess;
import java.sql.SQLException;

/**
 * RecipeBookUser - Class to implement the the IRecipeBookUser interface.
 */
public class RecipeBookUser implements IRecipeBookUser {
    private String accessToken;
    private Boolean authenticated;
    private String firstName;
    private String lastName;
    private String password;
    private int userId;
    private String userName;

    // region Constructors
    public RecipeBookUser() {
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.userId = 0;
        this.userName = "";
        this.authenticated = false;
        this.accessToken = "";
    }

    public RecipeBookUser(String firstName, String lastName, String password, int userId, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userId = userId;
        this.userName = userName;
        this.authenticated = false;
        this.accessToken = "";
    }

    public RecipeBookUser(String password, String userName) {
        this.firstName = "";
        this.lastName = "";
        this.password = password;
        this.userId = 0;
        this.userName = userName;
        this.authenticated = false;
        this.accessToken = "";
    }
    // end region

    /**
     * createUser - Method to create a new application user.
     */
    public void createUser() {
        try {
            RecipeBookUser result = UserDataAccess.createUser(this.firstName, this.lastName, this.password,
                    this.userName);
            if (result != null && result.getUserId() > 0) {
                this.userId = result.getUserId();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * getAccessToken - Getter for user's access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * getAuthenticated - Getter for authentication indicator.
     */
    public Boolean getAuthenticated() {
        return authenticated;
    }

    /**
     * getFirstName - Getter for user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getLastName - Getter for user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getUserId - Getter for user's user identification number.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * getUserName - Getter for user's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * login - Method to validate and login the user to the application.
     */
    public void login() {
        try {
            RecipeBookUser result = UserDataAccess.validateUser(this.password, this.userName);
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

    /**
     * setAccessToken - Mutator for user's access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * verifyDuplicateUsername - Method to verify if the username already exists in
     * the application database.
     */
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
