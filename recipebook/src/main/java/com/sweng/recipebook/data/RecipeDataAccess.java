package com.sweng.recipebook.data;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sweng.recipebook.models.Recipe;

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
     * @throws SQLException
     */
    public int addRecipe(Recipe recipe, int userId) throws SQLException {
        String dml = "INSERT INTO recipebook_recipes (recipe_name, recipe_description, serving_size, instructions, user_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(dml);
        Blob blob = connection.createBlob();
        blob.setBytes(1, recipe.getInstructions().getBytes());
        statement.setString(1, recipe.getRecipeName());
        statement.setString(2, recipe.getRecipeDescription());
        statement.setInt(3, recipe.getServingSize());
        statement.setBlob(4, blob);
        statement.setInt(5, userId);
        statement.execute();
        return getRecipeId(recipe.getRecipeName(), userId);
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
        statement.setInt(1, userId);
        statement.setString(2, recipeName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result = resultSet.getInt("recipe_id");
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
        statement.setInt(1, recipeId);
        statement.executeUpdate();
    }
}
