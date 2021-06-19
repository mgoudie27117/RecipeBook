package com.sweng.recipebook.models;

import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeVideo - Class for recipe videos that extends RecipeMedia.
 */
public class RecipeVideo extends RecipeMedia {

    // region Constructors
    public RecipeVideo() {
        super();
    }

    public RecipeVideo(MultipartFile file, String storagePath) {
        super(file, storagePath);
    }
    // end region
}
