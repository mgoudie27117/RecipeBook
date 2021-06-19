package com.sweng.recipebook.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeMedia - Abstact class for recipe media to extend.
 */
public abstract class RecipeMedia {
    protected MultipartFile file;
    protected String storagePath;

    // region Constructors
    public RecipeMedia() {
        file = null;
        storagePath = "";
    }

    public RecipeMedia(MultipartFile file, String storagePath) {
        this.file = file;
        this.storagePath = storagePath;
    }
    // end region

    /**
     * getFile - Getter for file variable.
     * 
     * @return - MultipartFile object.
     */
    public MultipartFile getFile() {
        return this.file;
    }

    /**
     * getStoragePath - Getter for storage path variable.
     * 
     * @return - String directory path.
     */
    public String getStoragePath() {
        return this.storagePath;
    }

    /**
     * save - Method to copy the MultipartFile to the storage path.
     * 
     * @throws IOException
     */
    public void save() {
        Path root = Paths.get(storagePath);
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
