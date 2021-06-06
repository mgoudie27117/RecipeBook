package com.sweng.recipebook;

import com.sweng.recipebook.controller.UserController;
import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.RecipeBookUser;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * UserControllerTest - Test class for the UserController class.
 */
public class UserControllerTest {

    private UserController controllerTest = new UserController();

    /**
     * createUserTest - Test method for UserController createuser functionality.
     * 
     * Related Test Case Number(s): T7
     * 
     * @throws SQLException
     */
    @Test
    public void createUserTest() throws SQLException {
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("userName", "mg");
        payload.put("password", "test123");
        payload.put("firstName", "Mike");
        payload.put("lastName", "Goudie");
        RecipeBookUser TestUser = controllerTest.createuser(payload);
        assertNotEquals(0, TestUser.getUserId());
        UserDataAccess.removeUser(TestUser.getUserId());
    }

    /**
     * loginTest - Test method for UserController login functionality.
     * 
     * Related Test Case Number(s): T3
     */
    @Test
    public void loginTest() {
        Map<String, String> payload1 = new HashMap<String, String>();
        payload1.put("userName", "mg");
        payload1.put("password", "test123");
        RecipeBookUser TestUser1 = controllerTest.login(payload1);
        assertEquals("", TestUser1.getFirstName());
        assertEquals("", TestUser1.getLastName());
        assertEquals(0, TestUser1.getUserId());
        assertEquals("mg", TestUser1.getUserName());
        assertEquals("", TestUser1.getAccessToken());
        Map<String, String> payload2 = new HashMap<String, String>();
        payload2.put("userName", "mgoudie");
        payload2.put("password", "TEST");
        RecipeBookUser TestUser2 = controllerTest.login(payload2);
        assertEquals("Michael", TestUser2.getFirstName());
        assertEquals("Goudie", TestUser2.getLastName());
        assertEquals(1, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(true, TestUser2.getAccessToken().length() > 1);
        Map<String, String> payload3 = new HashMap<String, String>();
        RecipeBookUser TestUser3 = controllerTest.login(payload3);
        assertEquals(TestUser3, null);
    }

    /**
     * verifyDuplicateUsernameTest - Test method for UserController testing if a
     * username already exists functionality.
     * 
     * Related Test Case Number(s): T8
     */
    @Test
    public void verifyDuplicateUsernameTest() {
        String TestUsername1 = "_";
        assertEquals(false, controllerTest.usernameexists(TestUsername1));
        String TestUsername2 = "mgoudie";
        assertEquals(true, controllerTest.usernameexists(TestUsername2));
        String TestUsername3 = "";
        assertEquals(false, controllerTest.usernameexists(TestUsername3));
    }
}