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
     * addFavoriteRecipe - Method to add a favorite recipe to the favorites table.
     * 
     * @param userId   - User id number.
     * @param recipeId - Recipe id number.
     * @throws SQLException
     */
    public void addFavoriteRecipe(int userId, int recipeId) throws SQLException {
        String dml = "MERGE INTO recipebook_favorite_recipes r USING ( SELECT ? AS user_id, ? AS recipe_id FROM dual) a ON (a.user_id = r.user_id AND a.recipe_id = r.recipe_id) WHEN NOT MATCHED THEN INSERT (user_id, recipe_id) VALUES (a.user_id, a.recipe_id)";
        PreparedStatement statement = connection.prepareStatement(dml);
        try {
            statement.setInt(1, userId);
            statement.setInt(2, recipeId);
            statement.execute();
        } finally {
            statement.close();
        }
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
     * getFavoriteRecipeIds - Method to retrieve favorite recipe ids for a given
     * user id number.
     * 
     * @param userId - User id number.
     * @return - List of favorite recipe ids.
     * @throws SQLException
     */
    public List<Integer> getFavoriteRecipeIds(int userId) throws SQLException {
        List<Integer> result = new ArrayList<Integer>();
        String query = "SELECT recipe_id FROM recipebook_favorite_recipes WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("recipe_id"));
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
     * isFavoriteRecipe - Method to test if a given recipe id for a user id is a
     * favorite.
     * 
     * @param recipeId - Recipe id number.
     * @param userId   - User id number.
     * @return - True if favorite, otherwise false.
     * @throws SQLException
     */
    public boolean isFavoriteRecipe(int recipeId, int userId) throws SQLException {
        String query = "SELECT recipe_id, user_id FROM recipebook_favorite_recipes WHERE recipe_id = ? AND user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.isBeforeFirst();
        } finally {
            statement.close();
        }
    }

    /**
     * isUserRecipe - Method to test if a given recipe id was shared by the user id.
     * 
     * @param recipeId - Recipe id number.
     * @param userId   - User id number.
     * @return - True if it is the user's recipe, otherwise false.
     * @throws SQLException
     */
    public boolean isUserRecipe(int recipeId, int userId) throws SQLException {
        String query = "SELECT recipe_id, user_id FROM recipebook_recipes WHERE recipe_id = ? AND user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.isBeforeFirst();
        } finally {
            statement.close();
        }
    }

    /**
     * removeFavoriteRecipe - Method to remove a favorite recipe from the favorites
     * table.
     * 
     * @param userId   - User id number.
     * @param recipeId - Recipe id number.
     * @throws SQLException
     */
    public void removeFavoriteRecipe(int userId, int recipeId) throws SQLException {
        String dml = "DELETE FROM recipebook_favorite_recipes WHERE user_id = ? AND recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        try {
            statement.setInt(1, userId);
            statement.setInt(2, recipeId);
            statement.execute();
        } finally {
            statement.close();
        }
    }

    /**
     * removeRecipe - Method to remove a recipe from the application recipe table.
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

    /**
     * updateRecipe - Method to update a recipe in the recipe table.
     * 
     * @param recipeId - Recipe id number to be updated.
     * @param recipe   - Recipe information.
     * @throws SQLException
     */
    public void updateRecipe(int recipeId, Recipe recipe) throws SQLException {
        String dml = "UPDATE recipebook_recipes SET recipe_description = ?, serving_size = ?, instructions = ? WHERE recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(dml);
        Blob blob = connection.createBlob();
        try {
            blob.setBytes(1, this.correctJSONCharacters(recipe.getInstructions()).getBytes());
            statement.setString(1, this.correctJSONCharacters(recipe.getRecipeDescription()));
            statement.setInt(2, recipe.getServingSize());
            statement.setBlob(3, blob);
            statement.setInt(4, recipeId);
            statement.execute();
        } finally {
            statement.close();
        }
    }
}
