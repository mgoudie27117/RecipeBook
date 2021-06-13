package com.sweng.recipebook;

import com.sweng.recipebook.models.RecipeBookUser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RecipeBookUserTest - Test class for the RecipeBookUser class.
 */
public class RecipeBookUserTest {

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
}
