package com.sweng.recipebook.data;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sweng.recipebook.models.IngredientComposite;
import com.sweng.recipebook.models.Recipe;
import com.sweng.recipebook.models.RecipeMediaComposite;
import com.sweng.recipebook.models.SharedRecipe;

/**
 * RecipeDataAccess - DataAccess class for application recipes.
 */
public class RecipeDataAccess extends DataAccess {

    public RecipeDataAccess() {
        super();
    }

    /**
     * addRecipe - Method to add a recipe to the recipes table.
     * 
     * @param recipe - Recipe to be added.
     * @param userId - User id adding the recipe.
     * @return - Added recipe id int.
     * @throws SQLException // this.correctJSONCharacters(
     */
    public int addRecipe(Recipe recipe, int userId) throws SQLException {
        String dml = "INSERT INTO recipebook_recipes (recipe_name, recipe_description, serving_size, instructions, user_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        Blob blob = connection.createBlob();
        try {
            blob.setBytes(1, this.correctJSONCharacters(recipe.getInstructions()).getBytes());
            statement.setString(1, this.correctJSONCharacters(recipe.getRecipeName()));
            statement.setString(2, this.correctJSONCharacters(recipe.getRecipeDescription()));
            statement.setInt(3, recipe.getServingSize());
            statement.setBlob(4, blob);
            statement.setInt(5, userId);
            statement.execute();
        } finally {
            statement.close();
        }
        return getRecipeId(recipe.getRecipeName(), userId);
    }

    /**
     * getHomeRecipes - Method to retrieve recipes for showcasing on the home page.
     * 
     * @return - List of Recipes.
     * @throws SQLException
     */
    public List<Recipe> getHomeRecipes() throws SQLException {
        List<Recipe> result = new ArrayList<Recipe>();
        String query = "SELECT recipe_id, recipe_name, recipe_description FROM recipebook_recipes ORDER BY recipe_id";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new SharedRecipe(resultSet.getInt("recipe_id"), resultSet.getString("recipe_name"),
                        resultSet.getString("recipe_description"), 0, "", new IngredientComposite()));
            }
        } finally {
            statement.close();
        }
        return result;
    }

    /**
     * getRecipe - Method to retrieve a recipe for a given id number.
     * 
     * @param recipeId             - Recipe id number.
     * @param ingredientComposite  - Recipe ingredients.
     * @param recipeMediaComposite - Recipe media.
     * @return - Recipe for the given id number.
     * @throws SQLException
     */
    public Recipe getRecipe(int recipeId, IngredientComposite ingredientComposite,
            RecipeMediaComposite recipeMediaComposite) throws SQLException {
        Recipe result = new SharedRecipe();
        String query = "SELECT recipe_id, recipe_name, recipe_description, serving_size, instructions, user_id AS shared_by_id, first_name || ' ' || SUBSTR(last_name, 1, 1) || '.' AS shared_by_name FROM recipebook_recipes JOIN recipebook_user USING (user_id) WHERE recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new SharedRecipe(resultSet.getInt("recipe_id"), resultSet.getString("recipe_name"),
                        resultSet.getString("recipe_description"), resultSet.getInt("serving_size"),
                        new String(resultSet.getBytes("instructions")), ingredientComposite, recipeMediaComposite,
                        resultSet.getString("shared_by_name"), resultSet.getInt("shared_by_id"));
            }
        } finally {
            statement.close();
        }
        return result;
    }

    /**
     * getRecipeId - Method to retrieve the recipe id for the given recipe and user
     * id.
     * 
     * @param recipeName - Shared recipe.
     * @param userId     - User id of shared recipe.
     * @return - Recipe id int.
     * @throws SQLException
     */
    public int getRecipeId(String recipeName, int userId) throws SQLException {
        int result = 0;
        String query = "SELECT recipe_id FROM recipebook_recipes WHERE user_id = ? AND recipe_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, userId);
            statement.setString(2, this.correctJSONCharacters(recipeName));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("recipe_id");
            }
        } finally {
            statement.close();
        }
        return result;
    }

    /**
     * removeRecipe - Method to remove a user from the application user table.
     * 
     * @param userId - Recipe id number to be removed.
     * @throws SQLException
     */
    public void removeRecipe(int recipeId) throws SQLException {
        String dml = "DELETE FROM recipebook_recipes WHERE recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        try {
            statement.setInt(1, recipeId);
            statement.executeUpdate();
        } finally {
            statement.close();
        }
    }
}
