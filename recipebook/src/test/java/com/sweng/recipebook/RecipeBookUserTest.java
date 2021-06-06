package com.sweng.recipebook;

import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.RecipeBookUser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;

/**
 * RecipeBookUserTest - Test class for the RecipeBookUser class.
 */
public class RecipeBookUserTest {

    /**
     * recipeBookUserObjectCreateUserTest - Test method for verify provided username
     * and password as valid for login.
     * 
     * Related Test Case Number(s): T10
     */
    @Test
    public void recipeBookUserObjectCreateUserTest() throws SQLException {
        RecipeBookUser TestUser1 = new RecipeBookUser("m", "g", "j", 0, "mg");
        TestUser1.createUser();
        assertNotEquals(0, TestUser1.getUserId());
        UserDataAccess.removeUser(TestUser1.getUserId());
    }

    /**
     * recipeBookUserObjectGetterTest - Test method for verify object getters with
     * contructor parameters.
     * 
     * Related Test Case Number(s): T5
     */
    @Test
    public void recipeBookUserObjectGetterTest() {
        RecipeBookUser TestUser1 = new RecipeBookUser("Michael", "Goudie", "Test123", 1, "mgoudie");
        assertEquals("Michael", TestUser1.getFirstName());
        assertEquals("Goudie", TestUser1.getLastName());
        assertEquals(1, TestUser1.getUserId());
        assertEquals("mgoudie", TestUser1.getUserName());
        assertEquals(false, TestUser1.getAuthenticated());
        assertEquals("", TestUser1.getAccessToken());
        RecipeBookUser TestUser2 = new RecipeBookUser("Test123", "mgoudie");
        assertEquals("", TestUser2.getFirstName());
        assertEquals("", TestUser2.getLastName());
        assertEquals(0, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(false, TestUser2.getAuthenticated());
        assertEquals("", TestUser2.getAccessToken());
    }

    /**
     * recipeBookUserObjectLoginTest - Test method for verify provided username and
     * password as valid for login.
     * 
     * Related Test Case Number(s): T4
     */
    @Test
    public void recipeBookUserObjectLoginTest() {
        RecipeBookUser TestUser1 = new RecipeBookUser("Test123", "mgoudie");
        TestUser1.login();
        assertEquals("", TestUser1.getFirstName());
        assertEquals("", TestUser1.getLastName());
        assertEquals(0, TestUser1.getUserId());
        assertEquals("mgoudie", TestUser1.getUserName());
        assertEquals(false, TestUser1.getAuthenticated());
        assertEquals("", TestUser1.getAccessToken());
        RecipeBookUser TestUser2 = new RecipeBookUser("TEST", "mgoudie");
        TestUser2.login();
        assertEquals("Michael", TestUser2.getFirstName());
        assertEquals("Goudie", TestUser2.getLastName());
        assertEquals(1, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(true, TestUser2.getAuthenticated());
        assertEquals("", TestUser2.getAccessToken());
    }

    /**
     * recipeBookUserObjectVerifyDuplicateUsernameTest - Test method for verify if
     * provided username already exists.
     * 
     * Related Test Case Number(s): T9
     */
    @Test
    public void recipeBookUserObjectVerifyDuplicateUsernameTest() {
        RecipeBookUser TestUser1 = new RecipeBookUser("TEST", "_");
        assertEquals(false, TestUser1.verifyDuplicateUsername());
        RecipeBookUser TestUser2 = new RecipeBookUser("TEST", "mgoudie");
        assertEquals(true, TestUser2.verifyDuplicateUsername());
    }
}
