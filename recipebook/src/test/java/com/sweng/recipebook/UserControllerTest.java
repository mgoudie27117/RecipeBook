package com.sweng.recipebook;

import com.sweng.recipebook.controller.UserController;
import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.User;
import com.sweng.recipebook.security.JWTHandler;
import com.sweng.recipebook.security.JWTStatus;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
        payload.put("securityQuestion", "What is your favorite color?");
        payload.put("securityAnswer", "Green");
        User TestUser = controllerTest.createuser(payload);
        assertNotEquals(0, TestUser.getUserId());
        new UserDataAccess().removeUser(TestUser.getUserId());
        User TestUserEmpty = controllerTest.createuser(new HashMap<String, String>());
        assertEquals(0, TestUserEmpty.getUserId());
    }

    /**
     * loginTest - Test method for UserController login functionality.
     * 
     * Related Test Case Number(s): T3
     * 
     * @throws SQLException
     */
    @Test
    public void loginTest() throws SQLException {
        Map<String, String> payload1 = new HashMap<String, String>();
        payload1.put("userName", "mg");
        payload1.put("password", "test123");
        User TestUser1 = controllerTest.login(payload1);
        assertEquals("", TestUser1.getFirstName());
        assertEquals("", TestUser1.getLastName());
        assertEquals(0, TestUser1.getUserId());
        assertEquals("mg", TestUser1.getUserName());
        assertEquals("", TestUser1.getAccessToken());
        Map<String, String> payload2 = new HashMap<String, String>();
        payload2.put("userName", "mgoudie");
        payload2.put("password", "TEST");
        User TestUser2 = controllerTest.login(payload2);
        assertEquals("Michael", TestUser2.getFirstName());
        assertEquals("Goudie", TestUser2.getLastName());
        assertEquals(1, TestUser2.getUserId());
        assertEquals("mgoudie", TestUser2.getUserName());
        assertEquals(true, TestUser2.getAccessToken().length() > 1);
        User TestUser3 = controllerTest.login(new HashMap<String, String>());
        assertEquals("", TestUser3.getFirstName());
        assertEquals("", TestUser3.getLastName());
        assertEquals(0, TestUser3.getUserId());
        assertEquals("", TestUser3.getUserName());
        assertEquals("", TestUser3.getAccessToken());
    }

    /**
     * securityAnswerTest - Test method for UserController to verify token for
     * provided security question answer.
     * 
     * Related Test Case Number(s): T29
     * 
     * @throws SQLException
     */
    @Test
    public void securityAnswerTest() throws SQLException {
        Map<String, String> payload = new HashMap<String, String>();
        String result1 = controllerTest.securityanswer(payload);
        assertEquals(result1.length() == 0, true);
        assertEquals(JWTStatus.INVALID, new JWTHandler().verifyToken(result1));
        payload.put("userName", "mgoudie");
        payload.put("securityAnswer", "Burlington");
        String result2 = controllerTest.securityanswer(payload);
        assertEquals(result2.length() > 0, true);
        assertEquals(JWTStatus.VALID, new JWTHandler().verifyToken(result2));

        payload.replace("securityAnswer", "WRONG");
        String result3 = controllerTest.securityanswer(payload);
        assertEquals(result3.length() == 0, true);
        assertEquals(JWTStatus.INVALID, new JWTHandler().verifyToken(result3));
    }

    /**
     * securityQuestionTest - Test method for UserController verifying security
     * question for a provided username.
     * 
     * Related Test Case Number(s): T28
     * 
     * @throws SQLException
     */
    @Test
    public void securityQuestionTest() throws SQLException {
        String userName = "mgoudie";
        String securityQuestion = controllerTest.securityquestion(userName);
        assertEquals(securityQuestion, "What city were you born in?");
    }

    /**
     * securityQuestionsTest - Test method for UserController testing all security
     * questions available for selection upon retrieval.
     * 
     * Related Test Case Number(s): T27
     * 
     * @throws SQLException
     */
    @Test
    public void securityQuestionsTest() throws SQLException {
        List<String> securityQuestions = controllerTest.securityquestions();
        assertEquals(3, securityQuestions.size());
        assertEquals(securityQuestions.get(0), "What city were you born in?");
        assertEquals(securityQuestions.get(1), "What is the last name of your favorite teacher?");
        assertEquals(securityQuestions.get(2), "What is your favorite color?");
    }

    /**
     * updatepasswordTest - Test method for UserController update password
     * functinality.
     * 
     * Related Test Case Number(s): T30
     * 
     * @throws SQLException
     */
    @Test
    public void updatepasswordTest() throws SQLException {
        String oldPassword = "TEST";
        String newPassword = "TEST123";
        Map<String, String> payload = new HashMap<String, String>();
        String result1 = controllerTest.updatepassword(payload);
        assertEquals("FAILED", result1);
        payload.put("userName", "mgoudie");
        payload.put("password", oldPassword);
        User TestUser = controllerTest.login(payload);
        payload.put("token", TestUser.getAccessToken());
        payload.replace("password", newPassword);
        String result2 = controllerTest.updatepassword(payload);
        assertEquals("SUCCESS", result2);
        payload.replace("password", oldPassword);
        controllerTest.updatepassword(payload);
    }

    /**
     * verifyDuplicateUsernameTest - Test method for UserController testing if a
     * username already exists functionality.
     * 
     * Related Test Case Number(s): T8
     * 
     * @throws SQLException
     */
    @Test
    public void verifyDuplicateUsernameTest() throws SQLException {
        String TestUsername1 = "_";
        assertEquals(false, controllerTest.usernameexists(TestUsername1));
        String TestUsername2 = "mgoudie";
        assertEquals(true, controllerTest.usernameexists(TestUsername2));
        String TestUsername3 = "";
        assertEquals(false, controllerTest.usernameexists(TestUsername3));
    }
}