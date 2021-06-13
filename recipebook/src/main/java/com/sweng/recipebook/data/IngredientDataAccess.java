package com.sweng.recipebook.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sweng.recipebook.models.Ingredient;

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
            statement.setInt(1, recipeId);
            statement.setString(2, ingredient.getIngredientName());
            statement.setDouble(3, ingredient.getPortionAmount());
            statement.setString(4, ingredient.getPortionMeasure());
            statement.executeUpdate();
        }
    }
}
