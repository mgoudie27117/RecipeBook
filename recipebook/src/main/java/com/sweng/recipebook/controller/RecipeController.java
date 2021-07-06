package com.sweng.recipebook.controller;

import com.google.gson.Gson;
import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.data.IngredientDataAccess;
import com.sweng.recipebook.data.RecipeDataAccess;
import com.sweng.recipebook.models.Ingredient;
import com.sweng.recipebook.models.Recipe;
import com.sweng.recipebook.models.RecipeIngredient;
import com.sweng.recipebook.models.RecipeMediaComposite;
import com.sweng.recipebook.models.SharedRecipe;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;

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
     * addfavoriterecipe - API call to add a favorite recipe for the user.
     * 
     * @param payload - Request with recipe id number and user id number.
     * @return - Updated list of favorite recipes.
     * @throws SQLException
     */
    @RequestMapping(value = "/addfavoriterecipe", method = RequestMethod.POST)
    public List<SharedRecipe> addfavoriterecipe(@RequestBody Map<String, String> payload) throws SQLException {
        int userId = JWT.getUserId(payload.get("token"));
        int recipeId = Integer.parseInt(payload.get("recipeId"));
        recipeDataAccess.addFavoriteRecipe(userId, recipeId);
        return getfavoriterecipes(payload.get("token"));
    }

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
     * getrecipe - API call to retieve a recipe for a given recipe id number.
     * 
     * @param recipeId - Recipe id number.
     * @return - Recipe for given parameter.
     * @throws SQLException
     * @throws IOException
     */
    @RequestMapping(value = "/getrecipe/{recipeId}", method = RequestMethod.GET)
    public Recipe getrecipe(@PathVariable int recipeId) throws SQLException, IOException {
        return recipeDataAccess.getRecipe(recipeId, ingredientDataAccess.getRecipeIngredients(recipeId),
                new RecipeMediaComposite());
    }

    /**
     * getfavoriterecipes - API call to retrieve a list of favorite recipes for a
     * user.
     * 
     * @param payload - Request with user token.
     * @return - List of favorite user recipes.
     * @throws SQLException
     */
    @RequestMapping(value = "/getfavoriterecipes", method = RequestMethod.GET)
    public List<SharedRecipe> getfavoriterecipes(@RequestBody Map<String, String> payload) throws SQLException {
        return getfavoriterecipes(payload.get("token"));
    }

    /**
     * getfavoriterecipes - Private helper to retrieve a list of favorite recipes
     * for a user.
     * 
     * @param token - User token.
     * @return - List of favorite user recipes.
     * @throws SQLException
     */
    private List<SharedRecipe> getfavoriterecipes(String token) throws SQLException {
        List<SharedRecipe> result = new ArrayList<SharedRecipe>();
        List<Integer> favoriteRecipeIds = recipeDataAccess.getFavoriteRecipeIds(JWT.getUserId(token));
        for (int recipeId : favoriteRecipeIds) {
            result.add((SharedRecipe) recipeDataAccess.getRecipe(recipeId,
                    ingredientDataAccess.getRecipeIngredients(recipeId), new RecipeMediaComposite()));
        }
        return result;
    }

    /**
     * gethomerecipes - API call to retrieve recipes for the home page.
     * 
     * @return - List of Recipes.
     * @throws SQLException
     */
    @RequestMapping(value = "/gethomerecipes", method = RequestMethod.GET)
    public List<Recipe> gethomerecipes() throws SQLException {
        return recipeDataAccess.getHomeRecipes();
    }

    /**
     * removefavoriterecipe - API call to remove a favorite recipe for a user.
     * 
     * @param payload - Request containing user token and recipe id.
     * @return - Updated list of favorite recipes.
     * @throws SQLException
     */
    @RequestMapping(value = "/removefavoriterecipe", method = RequestMethod.POST)
    public List<SharedRecipe> removefavoriterecipe(@RequestBody Map<String, String> payload) throws SQLException {
        int userId = JWT.getUserId(payload.get("token"));
        int recipeId = Integer.parseInt(payload.get("recipeId"));
        recipeDataAccess.removeFavoriteRecipe(userId, recipeId);
        return getfavoriterecipes(payload.get("token"));
    }

    /**
     * sharerecipe - API call to add recipes to the database.
     * 
     * @param payload - Map of request body information.
     * @return - Recipe id.
     * @throws SQLException
     */
    @RequestMapping(value = "/sharerecipe", method = RequestMethod.POST)
    public int sharerecipe(@RequestBody Map<String, Object> payload) throws SQLException {
        if (!payload.isEmpty()) {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(payload.get("SharedRecipe").toString()));
            reader.setLenient(true);
            SharedRecipe parseRecipe = gson.fromJson(reader, SharedRecipe.class);
            RecipeIngredient[] pareseIngredients = gson.fromJson(payload.get("Ingredients").toString(),
                    RecipeIngredient[].class);
            Recipe recipe = new SharedRecipe(parseRecipe.getRecipeName(), parseRecipe.getRecipeDescription(),
                    parseRecipe.getServingSize(), parseRecipe.getInstructions(), pareseIngredients);
            int userId = JWT.getUserId(payload.get("Token").toString());
            int recipeId = recipeDataAccess.addRecipe(recipe, userId);
            ingredientDataAccess.addRecipeIngredients(recipeId, recipe.getIngredients());
            return recipeId;
        } else {
            return 0;
        }
    }

    /**
     * updaterecipe - API call to update a recipe in the database.
     * 
     * @param payload - Map of request body information.
     * @return - Updated recipe.
     * @throws SQLException
     */
    @RequestMapping(value = "/updaterecipe", method = RequestMethod.POST)
    public Recipe updaterecipe(@RequestBody Map<String, Object> payload) throws SQLException {
        if (!payload.isEmpty()) {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(payload.get("SharedRecipe").toString()));
            reader.setLenient(true);
            SharedRecipe parseRecipe = gson.fromJson(reader, SharedRecipe.class);
            RecipeIngredient[] pareseIngredients = gson.fromJson(payload.get("Ingredients").toString(),
                    RecipeIngredient[].class);
            Recipe recipe = new SharedRecipe(parseRecipe.getRecipeName(), parseRecipe.getRecipeDescription(),
                    parseRecipe.getServingSize(), parseRecipe.getInstructions(), pareseIngredients);
            int userId = JWT.getUserId(payload.get("Token").toString());
            int recipeId = recipeDataAccess.getRecipeId(recipe.getRecipeName(), userId);
            recipeDataAccess.updateRecipe(recipeId, recipe);
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.getIngredientId() == 0) {
                    ingredientDataAccess.addRecipeIngredient(recipeId, ingredient);
                } else if (ingredient.getIngredientId() < 0) {
                    ingredientDataAccess.removeIngredient(recipeId, ingredient.getIngredientName());
                } else {
                    ingredientDataAccess.updateIngredient(ingredient);
                }
            }
            return recipeDataAccess.getRecipe(recipeId, ingredientDataAccess.getRecipeIngredients(recipeId),
                    new RecipeMediaComposite());
        } else {
            return new SharedRecipe();
        }
    }
}
