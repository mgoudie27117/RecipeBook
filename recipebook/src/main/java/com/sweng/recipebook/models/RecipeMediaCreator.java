package com.sweng.recipebook.models;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeMediaCreator - Abstract class for factory to extend from.
 */
public abstract class RecipeMediaCreator {

    public RecipeMediaCreator() {

    }

    /**
     * createRecipeMedia - Method to return a derived RecipeMedia object based on
     * the parameter MultipartFile extension.
     * 
     * @param file        - MultipartFile object.
     * @param storagePath - File storage path.
     * @return RecipeMedia object.
     */
    public RecipeMedia createRecipeMedia(MultipartFile file, String storagePath) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        switch (extension.toLowerCase()) {
            case "jpeg":
                return new RecipeImage(file, storagePath);
            case "jpg":
                return new RecipeImage(file, storagePath);
            case "mp4":
                return new RecipeVideo(file, storagePath);
            default:
                return null;
        }
    }
}
