package com.sweng.recipebook.models;

/**
 * IRecipeBookUser - Interface to implement an application user.
 */
public interface IRecipeBookUser {

    /**
     * createUser - Method to create a new application user.
     */
    void createUser();

    /**
     * getAccessToken - Getter for user's access token.
     */
    String getAccessToken();

    /**
     * getAuthenticated - Getter for authentication indicator.
     */
    Boolean getAuthenticated();

    /**
     * getFirstName - Getter for user's first name.
     */
    String getFirstName();

    /**
     * getLastName - Getter for user's last name.
     */
    String getLastName();

    /**
     * getUserId - Getter for user's user identification number.
     */
    int getUserId();

    /**
     * getUserName - Getter for user's username.
     */
    String getUserName();

    /**
     * login - Method to validate and login the user to the application.
     */
    void login();

    /**
     * setAccessToken - Mutator for user's access token.
     */
    void setAccessToken(String accessToken);

    /**
     * verifyDuplicateUsername - Method to verify if the username already exists in
     * the application database.
     */
    Boolean verifyDuplicateUsername();
}
