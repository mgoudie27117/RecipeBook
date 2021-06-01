package com.sweng.recipebook;

import com.sweng.recipebook.controller.UserController;
import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserControllerTest {

    UserController controllerTest = new UserController();

    @Test
    public void loginTest() {
        Map<String, String> payload1 = new HashMap<String, String>();
        payload1.put("userName", "mg");
        payload1.put("password", "test123");
        User TestUser1 = controllerTest.login(payload1);
        assertEquals("", TestUser1.getFirstName());
        assertEquals("", TestUser1.getLastName());
        assertEquals(0, TestUser1.getUserId());
        assertEquals("mg", TestUser1.getUserName());

        Map<String, String> payload2 = new HashMap<String, String>();
        payload2.put("userName", "mgoudie");
        payload2.put("password", "TEST");
        User TestUser2 = controllerTest.login(payload2);
        assertEquals("Michael", TestUser2.getFirstName());
        assertEquals("Goudie", TestUser2.getLastName());
        assertEquals(1, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());

        Map<String, String> payload3 = new HashMap<String, String>();
        User TestUser3 = controllerTest.login(payload3);
        assertEquals(TestUser3, null);
    }

    @Test // NEW
    public void verifyDuplicateUsernameTest() {
        String TestUsername1 = "_";
        assertEquals(false, controllerTest.usernameexists(TestUsername1));

        String TestUsername2 = "mgoudie";
        assertEquals(true, controllerTest.usernameexists(TestUsername2));
    }

    @Test // NEW
    public void createUserTest() throws SQLException {
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("userName", "mg");
        payload.put("password", "j");
        payload.put("firstName", "m");
        payload.put("lastName", "g");
        User TestUser = controllerTest.createuser(payload);
        assertNotEquals(0, TestUser.getUserId());
        UserDataAccess.removeUser(TestUser.getUserId());
    }
}