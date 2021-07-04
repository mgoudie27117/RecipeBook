package com.sweng.recipebook.models;

/**
 * SharedRecipe - Class for shared recipes that extends Recipe.
 */
public class SharedRecipe extends Recipe {

    protected int sharedById;
    protected String sharedByName;

    // region Constructors
    public SharedRecipe() {
        super();
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName) {
        super(recipeId, recipeName);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions) {
        super(recipeName, recipeDescription, servingSize, instructions);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        super(recipeName, recipeDescription, servingSize, instructions, ingredients);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(String recipeName, String recipeDescription, int servingSize, String instructions,
            Ingredient[] ingredients) {
        super(recipeName, recipeDescription, servingSize, instructions, ingredients);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients, RecipeMediaComposite recipeMedia) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients, RecipeMediaComposite recipeMedia, String sharedByName, int sharedById) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia);
        this.sharedById = sharedById;
        this.sharedByName = sharedByName;
    }
    // end region
}