package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.sweng.recipebook.controller.RecipeMediaController;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * RecipeMediaControllerTest - Test class for the RecipeMediaController class.
 */
public class RecipeMediaControllerTest {

    private RecipeMediaController recipeMediaControllerTest = new RecipeMediaController();

    /**
     * checkRetrieveRecipeMediaNames - Test method for RecipeMediaController recipe
     * media names for a given id number.
     * 
     * Related Test Case Number(s): T35
     * 
     * @throws SQLException
     */
    @Test
    public void checkRetrieveRecipeMediaNames() throws SQLException {
        List<String> mediaListTest = recipeMediaControllerTest.retrieverecipemedianames("0");
        assertEquals(mediaListTest.size(), 1);
        assertEquals(mediaListTest.get(0), "DEFAULT.JPG");
        mediaListTest = recipeMediaControllerTest.retrieverecipemedianames("TEST");
        assertEquals(mediaListTest.size(), 3);
        mediaListTest = recipeMediaControllerTest.retrieverecipemedianames("NONE");
        assertEquals(mediaListTest.size(), 1);
    }

    /**
     * checkRetrieveRecipeMedia - Test method for verifying file response success
     * for a given id and filename.
     * 
     * Related Test Case Number(s): T36
     * 
     * @throws FileNotFoundException
     * @throws SQLException
     */
    @Test
    public void checkRetrieveRecipeMedia() throws FileNotFoundException, SQLException {
        ResponseEntity<Object> entityTest = recipeMediaControllerTest.retrieverecipemedia("DEFAULT", "DEFAULT.JPG");
        assertEquals(entityTest.getStatusCode(), HttpStatus.OK);
    }
}
