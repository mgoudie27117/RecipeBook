package com.sweng.recipebook;

import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;

public class UserTest {

    @Test
    public void userObjectGetterTest() {
        User TestUser1 = new User("Michael", "Goudie", "Test123", 1, "mgoudie");
        assertEquals("Michael", TestUser1.getFirstName());
        assertEquals("Goudie", TestUser1.getLastName());
        assertEquals(1, TestUser1.getUserId());
        assertEquals("mgoudie", TestUser1.getUserName());
        assertEquals(false, TestUser1.getAuthenticated());

        User TestUser2 = new User("Test123", "mgoudie");
        assertEquals("", TestUser2.getFirstName());
        assertEquals("", TestUser2.getLastName());
        assertEquals(0, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(false, TestUser2.getAuthenticated());
    }

    @Test
    public void userObjectLoginTest() {
        User TestUser1 = new User("Test123", "mgoudie");
        TestUser1.login();
        assertEquals("", TestUser1.getFirstName());
        assertEquals("", TestUser1.getLastName());
        assertEquals(0, TestUser1.getUserId());
        assertEquals("mgoudie", TestUser1.getUserName());
        assertEquals(false, TestUser1.getAuthenticated());

        User TestUser2 = new User("TEST", "mgoudie");
        TestUser2.login();
        assertEquals("Michael", TestUser2.getFirstName());
        assertEquals("Goudie", TestUser2.getLastName());
        assertEquals(1, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(true, TestUser2.getAuthenticated());
    }

    @Test // NEW
    public void userObjectVerifyDuplicateUsernameTest() {
        User TestUser1 = new User("TEST", "_");
        assertEquals(false, TestUser1.verifyDuplicateUsername());

        User TestUser2 = new User("TEST", "mgoudie");
        assertEquals(true, TestUser2.verifyDuplicateUsername());
    }

    @Test // NEW
    public void userObjectCreateUserTest() throws SQLException {
        User TestUser1 = new User("m", "g", "j", 0, "mg");
        TestUser1.createUser();
        assertNotEquals(0, TestUser1.getUserId());
        UserDataAccess.removeUser(TestUser1.getUserId());
    }
}
