package com.sweng.recipebook.models;

/**
 * RecipeReview - Class for a recipe review that extends Review.
 */
public class RecipeReview extends Review {

    // region Constructors
    public RecipeReview() {
        super();
    }

    public RecipeReview(String critic, int rating, String comments) {
        super(critic, rating, comments);
    }
    // end region
}
