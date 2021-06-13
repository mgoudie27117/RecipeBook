package com.sweng.recipebook.controller;

import com.google.gson.Gson;
import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.data.IngredientDataAccess;
import com.sweng.recipebook.data.RecipeDataAccess;
import com.sweng.recipebook.models.Recipe;
import com.sweng.recipebook.models.RecipeIngredient;
import com.sweng.recipebook.models.SharedRecipe;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RecipeController - REST controller for all API calls related to application
 * recipes.
 */
@RestController
@RequestMapping("/api/recipe")
public class RecipeController extends Controller {

    private RecipeDataAccess recipeDataAccess = (RecipeDataAccess) new DataAccessConcreteCreator()
            .createDataAccess("recipe");
    private IngredientDataAccess ingredientDataAccess = (IngredientDataAccess) new DataAccessConcreteCreator()
            .createDataAccess("ingredient");

    /**
     * checkuserrecipeexists - API call to check if the user has already shared this
     * recipe name.
     * 
     * @param payload - Request with recipe name and token.
     * @return - True if exists, false otherwise.
     * @throws SQLException
     */
    @RequestMapping(value = "/checkuserrecipeexists", method = RequestMethod.POST)
    public Boolean checkuserrecipeexists(@RequestBody Map<String, String> payload) throws SQLException {
        return recipeDataAccess.getRecipeId(payload.get("recipeName"), JWT.getUserId(payload.get("token"))) > 0;
    }

    /**
     * sharerecipe - API call to add recipes to the database.
     * 
     * @param payload - Map of request body information.
     * @return - String result message.
     * @throws SQLException
     */
    @RequestMapping(value = "/sharerecipe", method = RequestMethod.POST)
    public String sharerecipe(@RequestBody Map<String, Object> payload) throws SQLException {
        if (payload.containsKey("SharedRecipe") && payload.containsKey("Ingredients") && payload.containsKey("Token")) {
            Gson gson = new Gson();
            SharedRecipe parseRecipe = gson.fromJson(payload.get("SharedRecipe").toString(), SharedRecipe.class);
            RecipeIngredient[] pareseIngredients = gson.fromJson(payload.get("Ingredients").toString(),
                    RecipeIngredient[].class);
            Recipe recipe = new SharedRecipe(parseRecipe.getRecipeName(), parseRecipe.getRecipeDescription(),
                    parseRecipe.getServingSize(), parseRecipe.getInstructions(), pareseIngredients);
            int userId = JWT.getUserId(payload.get("Token").toString());
            int recipeId = recipeDataAccess.addRecipe(recipe, userId);
            ingredientDataAccess.addRecipeIngredient(recipeId, recipe.getIngredients());
            return "SUCCESS";
        } else {
            return "MISSING_REQUEST_INFORMATION";
        }
    }
}
