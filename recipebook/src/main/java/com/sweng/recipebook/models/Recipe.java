package com.sweng.recipebook.models;

import java.util.ArrayList;

/**
 * Recipe - Abstact class for recipes to extend.
 */
public abstract class Recipe {
    protected int recipeId;
    protected String recipeName;
    protected String recipeDescription;
    protected int servingSize;
    protected String instructions;
    protected IngredientComposite ingredients;

    // region Constructors
    public Recipe() {
        this.recipeId = 0;
        this.recipeName = "";
        this.recipeDescription = "";
        this.servingSize = 0;
        this.instructions = "";
        this.ingredients = new IngredientComposite();
    }

    public Recipe(String recipeName, String recipeDescription, int servingSize, String instructions) {
        this.recipeId = 0;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.servingSize = servingSize;
        this.instructions = instructions;
        this.ingredients = new IngredientComposite();
    }

    public Recipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        this.recipeId = 0;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.servingSize = servingSize;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public Recipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.servingSize = servingSize;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public Recipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            Ingredient[] ingredients) {
        this.recipeId = 0;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.servingSize = servingSize;
        this.instructions = instructions;
        this.ingredients = new IngredientComposite();
        for (Ingredient ingredient : ingredients) {
            this.ingredients.addIngredient(ingredient);
        }
    }
    // end region

    /**
     * getRecipeId - Getter for recipe id number.
     * 
     * @return - Recipe id int.
     */
    public int getRecipeId() {
        return this.recipeId;
    }

    /**
     * getRecipeName - Getter for recipe's name.
     * 
     * @return - Recipe name String.
     */
    public String getRecipeName() {
        return this.recipeName;
    }

    /**
     * getRecipeDescription - Getter for recipe's description.
     * 
     * @return - Recipe description String.
     */
    public String getRecipeDescription() {
        return this.recipeDescription;
    }

    /**
     * getServingSize - Getter for recipe's serving size.
     * 
     * @return - Serving size int.
     */
    public int getServingSize() {
        return this.servingSize;
    }

    /**
     * getInstructions - Getter for recipe's instructions.
     * 
     * @return - Instruction String.
     */
    public String getInstructions() {
        return this.instructions;
    }

    /**
     * getIngredients - Getter for recipe's ingredients.
     * 
     * @return - ArrayList of recipe ingredients.
     */
    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients.getIngredients();
    }
}