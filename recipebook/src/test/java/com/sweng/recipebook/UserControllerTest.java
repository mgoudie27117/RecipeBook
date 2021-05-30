package com.sweng.recipebook;

import com.sweng.recipebook.controller.UserController;
import com.sweng.recipebook.models.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}