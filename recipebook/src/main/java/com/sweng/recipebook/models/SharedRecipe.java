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
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia,
                new ReviewComposite());
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients, RecipeMediaComposite recipeMedia, ReviewComposite reviews) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia, reviews);
        this.sharedById = 0;
        this.sharedByName = "";
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients, RecipeMediaComposite recipeMedia, String sharedByName, int sharedById) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia,
                new ReviewComposite());
        this.sharedById = sharedById;
        this.sharedByName = sharedByName;
    }

    public SharedRecipe(int recipeId, String recipeName, String recipeDescription, int servingSize, String instructions,
            IngredientComposite ingredients, RecipeMediaComposite recipeMedia, String sharedByName, int sharedById,
            ReviewComposite reviews) {
        super(recipeId, recipeName, recipeDescription, servingSize, instructions, ingredients, recipeMedia, reviews);
        this.sharedById = sharedById;
        this.sharedByName = sharedByName;
        this.reviews = reviews;
    }
    // end region
}