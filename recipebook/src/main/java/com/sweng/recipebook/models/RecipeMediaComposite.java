package com.sweng.recipebook.models;

import java.util.ArrayList;

/**
 * RecipeMediaComposite - RecipeMedia class that extends RecipeMedia to
 * implement composite pattern.
 */
public class RecipeMediaComposite extends RecipeMedia {

    private ArrayList<RecipeMedia> recipeMediaList;

    public RecipeMediaComposite() {
        recipeMediaList = new ArrayList<RecipeMedia>();
    }

    /**
     * addRecipeMedia - Method to add a recipe media to the composite.
     * 
     * @param RecipeMedia - Recipe media to add.
     */
    public void addRecipeMedia(RecipeMedia recipeMedia) {
        recipeMediaList.add(recipeMedia);
    }

    /**
     * getRecipeMedia - Getter for composite recipe media.
     * 
     * @return - ArrayList of recipe media.
     */
    public ArrayList<RecipeMedia> getRecipeMedia() {
        return recipeMediaList;
    }

    /**
     * removeRecipeMedia - Method to remove a recipe media from the composite.
     * 
     * @param recipeMedia - Recipe media to remove.
     */
    public void removeRecipeMedia(RecipeMedia recipeMedia) {
        recipeMediaList.remove(recipeMedia);
    }
}
