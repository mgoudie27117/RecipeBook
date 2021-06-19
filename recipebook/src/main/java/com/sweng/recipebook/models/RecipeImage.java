package com.sweng.recipebook.models;

import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeImage - Class for recipe images that extends RecipeMedia.
 */
public class RecipeImage extends RecipeMedia {

    // region Constructors
    public RecipeImage() {
        super();
    }

    public RecipeImage(MultipartFile file, String storagePath) {
        super(file, storagePath);
    }
    // end region
}
