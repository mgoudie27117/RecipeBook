package com.sweng.recipebook.models;

/**
 * User - Abstract class to define an application user.
 */
public abstract class User {
    protected String accessToken;
    protected Boolean authenticated;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected int userId;
    protected String userName;

    // region Constructors
    public User() {
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.userId = 0;
        this.userName = "";
        this.authenticated = false;
        this.accessToken = "";
    }

    public User(String firstName, String lastName, String password, int userId, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userId = userId;
        this.userName = userName;
        this.authenticated = false;
        this.accessToken = "";
    }

    public User(String password, String userName) {
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
     * setAccessToken - Mutator for user's access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * setAuthenticated - Mutator for user's authenticated status.
     */
    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }
}
