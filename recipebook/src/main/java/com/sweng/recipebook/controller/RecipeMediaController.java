package com.sweng.recipebook.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import com.sweng.recipebook.data.ConfigDataAccess;
import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.models.RecipeMedia;
import com.sweng.recipebook.models.RecipeMediaConcreteCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeMediaController - REST controller for all API calls related to
 * application recipe media.
 */
@RestController
@RequestMapping("/api/recipemedia")
public class RecipeMediaController extends Controller {

    private ConfigDataAccess configDataAccess = (ConfigDataAccess) new DataAccessConcreteCreator()
            .createDataAccess("config");

    /**
     * directoryVerfication - Helper method to create directories as needed for
     * recipe media storage.
     * 
     * @param directoryPath - Directory path to check and create if does not exist.
     */
    private void directoryVerfication(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * sharerecipe - API call to add recipe media to the application file share.
     * 
     * @param files    - File array of 1 or more files to be saved to the recipe id
     *                 directory.
     * @param recipeId - Recipe id of the related files.
     * @return - String message of file share upload status.
     * @throws SQLException
     */
    @RequestMapping(value = "/uploadrecipemedia/{recipeId}")
    public String uploadrecipemedia(@RequestParam(value = "files", required = false) MultipartFile[] files,
            @PathVariable String recipeId) throws SQLException {
        try {
            String dbConfig = configDataAccess.getConfig("FILESHARE_PATH");
            directoryVerfication(dbConfig);
            String fileSharePath = dbConfig + "\\" + recipeId;
            directoryVerfication(fileSharePath);
            Arrays.asList(files).stream().forEach(file -> {
                RecipeMedia recipeMedia = new RecipeMediaConcreteCreator().createRecipeMedia(file, fileSharePath);
                if (recipeMedia != null) {
                    recipeMedia.save();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }
}
