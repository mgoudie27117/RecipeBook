package com.sweng.recipebook.models;

/**
 * SharedRecipe - Class for shared recipes that extends Recipe.
 */
public class SharedRecipe extends Recipe {
    // region Constructors
    public SharedRecipe() {
        super();
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions) {
        super(recipeName, recipeDescription, servingSize, instructions);
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        super(recipeName, recipeDescription, servingSize, instructions, ingredients);
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients);
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            Ingredient[] ingredients) {
        super(recipeName, recipeDescription, servingSize, instructions, ingredients);
    }
    // end region
}