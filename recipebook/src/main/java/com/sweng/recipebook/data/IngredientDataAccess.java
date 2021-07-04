package com.sweng.recipebook.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sweng.recipebook.models.Ingredient;
import com.sweng.recipebook.models.IngredientComposite;
import com.sweng.recipebook.models.RecipeIngredient;

/**
 * IngredientDataAccess - DataAccess class for application ingredients.
 */
public class IngredientDataAccess extends DataAccess {

    public IngredientDataAccess() {
        super();
    }

    /**
     * addRecipeIngredient - Method to add ingredients for a given recipe id.
     * 
     * @param recipeId    - Recipe id of ingredients to be added.
     * @param ingredients - Ingredients to the added.
     * @throws SQLException
     */
    public void addRecipeIngredient(int recipeId, ArrayList<Ingredient> ingredients) throws SQLException {
        for (Ingredient ingredient : ingredients) {
            String dml = "INSERT INTO recipebook_ingredients (recipe_id, ingredient_name, portion_measure, measure_unit_id) VALUES (?, ?, ?, (SELECT measure_unit_id FROM recipebook_measure_unit WHERE measure_unit = ?))";
            PreparedStatement statement = connection.prepareStatement(dml);
            try {
                statement.setInt(1, recipeId);
                statement.setString(2, ingredient.getIngredientName().replaceAll("_", " "));
                statement.setDouble(3, ingredient.getPortionAmount());
                statement.setString(4, ingredient.getPortionMeasure());
                statement.executeUpdate();
            } finally {
                statement.close();
            }
        }
    }

    /**
     * getRecipeIngredients - Method to retrieve ingredients for a given recipe id.
     * 
     * @param recipeId - Recipe id number to retrieve ingredients for.
     * @return - Recipe ingredients.
     * @throws SQLException
     */
    public IngredientComposite getRecipeIngredients(int recipeId) throws SQLException {
        IngredientComposite result = new IngredientComposite();
        String query = "SELECT ingredient_id, ingredient_name, portion_measure, measure_unit FROM recipebook_ingredients JOIN recipebook_measure_unit USING (measure_unit_id) WHERE recipe_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setInt(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.addIngredient(
                        new RecipeIngredient(resultSet.getInt("ingredient_id"), resultSet.getString("ingredient_name"),
                                resultSet.getDouble("portion_measure"), resultSet.getString("measure_unit")));
            }
        } finally {
            statement.close();
        }
        return result;
    }
}
