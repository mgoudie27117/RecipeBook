package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.sweng.recipebook.models.RecipeBookUser;
import com.sweng.recipebook.security.JWTHandler;
import com.sweng.recipebook.security.JWTStatus;

import org.junit.jupiter.api.Test;

public class JWTHandlerTest {
    private static final JWTHandler JWT = new JWTHandler();

    /**
     * generateTokenTest - Test method for generating a token and verifying mutator.
     * 
     * Related Test Case Number(s): T11
     */
    @Test
    public void generateTokenTest() {
        RecipeBookUser TestUser = new RecipeBookUser("TEST", "mgoudie");
        assertEquals("", TestUser.getAccessToken());
        String testToken = JWT.generateToken(TestUser);
        TestUser.setAccessToken(testToken);
        assertEquals(testToken, TestUser.getAccessToken());
    }

    /**
     * refreshTokenTest - Test method for verifying new tokens generated when
     * refreshed.
     * 
     * Related Test Case Number(s): T12
     */
    @Test
    public void refreshTokenTest() {
        RecipeBookUser TestUser = new RecipeBookUser("TEST", "mgoudie");
        String testToken1 = JWT.generateToken(TestUser);
        String testToken2 = JWT.refreshToken(testToken1);
        assertNotEquals(testToken1, testToken2);
    }

    /**
     * verifyTokenTest - Test method for verifying the status of a token.
     * 
     * Related Test Case Number(s): T13
     */
    @Test
    public void verifyTokenTest() throws InterruptedException {
        RecipeBookUser TestUser = new RecipeBookUser("TEST", "mgoudie");
        assertEquals(JWTStatus.INVALID, JWT.verifyToken(TestUser.getAccessToken()));
        String testToken = JWT.generateToken(TestUser);
        assertEquals(JWTStatus.VALID, JWT.verifyToken(testToken));
        Thread.sleep(70000);
        assertEquals(JWTStatus.EXPIRED, JWT.verifyToken(testToken));
    }
}
