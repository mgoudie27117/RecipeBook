package com.sweng.recipebook.models;

/**
 * RecipeIngredient - Class to extend Ingredient for ingredients for recipes.
 */
public class RecipeIngredient extends Ingredient {
    // region Constructors
    public RecipeIngredient() {
        super();
    }

    public RecipeIngredient(String ingredientName, double portionAmount, String portionMeasure) {
        super(ingredientName, portionAmount, portionMeasure);
    }

    public RecipeIngredient(int ingredientId, String ingredientName, double portionAmount, String portionMeasure) {
        super(ingredientId, ingredientName, portionAmount, portionMeasure);
    }
    // end region
}
