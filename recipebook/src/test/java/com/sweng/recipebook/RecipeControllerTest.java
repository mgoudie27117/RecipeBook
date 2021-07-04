package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sweng.recipebook.controller.RecipeController;
import com.sweng.recipebook.controller.UserController;
import com.sweng.recipebook.data.RecipeDataAccess;
import com.sweng.recipebook.models.Ingredient;
import com.sweng.recipebook.models.Recipe;
import com.sweng.recipebook.models.RecipeIngredient;
import com.sweng.recipebook.models.SharedRecipe;
import com.sweng.recipebook.models.User;
import org.junit.jupiter.api.Test;

/**
 * RecipeControllerTest - Test class for the RecipeController class.
 */
public class RecipeControllerTest {

    private RecipeController recipeControllerTest = new RecipeController();
    private UserController userControllerTest = new UserController();

    /**
     * checkUserRecipeExistsAndShareTest - Test method for RecipeController recipe
     * sharing functionality.
     * 
     * Related Test Case Number(s): T16
     * 
     * @throws SQLException
     */
    @Test
    public void checkUserRecipeExistsAndShareTest() throws SQLException {
        Map<String, String> userPayload = new HashMap<String, String>();
        userPayload.put("userName", "mgoudie");
        userPayload.put("password", "TEST");
        User testUser = userControllerTest.login(userPayload);
        Map<String, String> payload1 = new HashMap<String, String>();
        payload1.put("recipeName", "TEST_NAME");
        payload1.put("token", testUser.getAccessToken());
        assertEquals(recipeControllerTest.checkuserrecipeexists(payload1), false);
        Map<String, Object> payload2 = new HashMap<String, Object>();
        payload2.put("SharedRecipe",
                new Gson().toJson(new SharedRecipe("TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS")));
        payload2.put("Ingredients",
                new Gson().toJson(new Ingredient[] { new RecipeIngredient("TEST_I1", 1.01, "cup") }));
        payload2.put("Token", testUser.getAccessToken());
        int testResult1 = recipeControllerTest.sharerecipe(payload2);
        assertEquals(true, testResult1 > 0);
        assertEquals(recipeControllerTest.checkuserrecipeexists(payload1), true);
        Map<String, Object> payload3 = new HashMap<String, Object>();
        int testResult2 = recipeControllerTest.sharerecipe(payload3);
        assertEquals(true, testResult2 == 0);
        new RecipeDataAccess().removeRecipe(new RecipeDataAccess().getRecipeId("TEST_NAME", testUser.getUserId()));
    }

    /**
     * checkGetHomeRecipes - Test method for RecipeController verification of home
     * page recipe retrieval.
     * 
     * Related Test Case Number(s): T33
     * 
     * @throws SQLException
     */
    @Test
    public void checkGetHomeRecipes() throws SQLException {
        List<Recipe> recipesTest = recipeControllerTest.gethomerecipes();
        assertEquals(true, recipesTest.size() > 0);
        for (Recipe recipe : recipesTest) {
            assertEquals(recipe instanceof SharedRecipe, true);
        }
    }

    /**
     * checkGetHomeRecipes - Test method for RecipeController verification of
     * retrieval of a recipe for a given recipe id.
     * 
     * Related Test Case Number(s): T34
     * 
     * @throws SQLException
     * @throws IOException
     */
    @Test
    public void checkGetRecipe() throws SQLException, IOException {
        Map<String, String> userPayload = new HashMap<String, String>();
        userPayload.put("userName", "mgoudie");
        userPayload.put("password", "TEST");
        User testUser = userControllerTest.login(userPayload);
        Map<String, Object> payload2 = new HashMap<String, Object>();
        payload2.put("SharedRecipe",
                new Gson().toJson(new SharedRecipe("TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS")));
        payload2.put("Ingredients",
                new Gson().toJson(new Ingredient[] { new RecipeIngredient("TEST_I1", 1.01, "cup") }));
        payload2.put("Token", testUser.getAccessToken());
        int recipeId = recipeControllerTest.sharerecipe(payload2);
        Recipe testRecipe = recipeControllerTest.getrecipe(recipeId);
        assertEquals(recipeId, testRecipe.getRecipeId());
        assertEquals("TEST_NAME", testRecipe.getRecipeName());
        assertEquals(true, testRecipe.getIngredients().size() > 0);
        new RecipeDataAccess().removeRecipe(new RecipeDataAccess().getRecipeId("TEST_NAME", testUser.getUserId()));
    }
}
