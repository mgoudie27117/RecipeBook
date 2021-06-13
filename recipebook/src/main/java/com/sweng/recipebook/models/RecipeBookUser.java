package com.sweng.recipebook.models;

/**
 * RecipeBookUser - Class to extend the the User abstract class.
 */
public class RecipeBookUser extends User {

    // region Constructors
    public RecipeBookUser() {
        super();
    }

    public RecipeBookUser(String firstName, String lastName, String password, int userId, String userName) {
        super(firstName, lastName, password, userId, userName);
    }

    public RecipeBookUser(String password, String userName) {
        super(password, userName);
    }
    // end region
}
