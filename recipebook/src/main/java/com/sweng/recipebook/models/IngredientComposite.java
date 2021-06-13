package com.sweng.recipebook.models;

import java.util.ArrayList;

/**
 * IngredientComposite - Ingredient class that extends Ingredient to implement
 * composite pattern.
 */
public class IngredientComposite extends Ingredient {

    private ArrayList<Ingredient> ingredients;

    public IngredientComposite() {
        ingredients = new ArrayList<Ingredient>();
    }

    /**
     * addIngredient - Method to add an ingredient to the composite.
     * 
     * @param ingredient - Ingredient to add.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * getIngredients - Getter for composite ingredients.
     * 
     * @return - ArrayList of ingredients.
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * removeIngredient - Method to remove an ingredient from the composite.
     * 
     * @param ingredient - Ingredient to remove.
     */
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
}
