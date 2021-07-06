package com.sweng.recipebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sweng.recipebook.data.ConfigDataAccess;
import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.models.RecipeMedia;
import com.sweng.recipebook.models.RecipeMediaConcreteCreator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * removerecipemedia - API call to remove a media file.
     * 
     * @param recipeId - Recipe id number.
     * @param file     - File name.
     * @throws SQLException
     */
    @RequestMapping(value = "/removerecipemedia/{recipeId}/{file}", method = RequestMethod.POST)
    public void removerecipemedia(@PathVariable String recipeId, @PathVariable String file) throws SQLException {
        File requestFile = new File(configDataAccess.getConfig("FILESHARE_PATH") + "\\" + recipeId + "\\" + file);
        if (requestFile.exists()) {
            requestFile.delete();
        }
    }

    /**
     * retrieverecipemedia - API call to retrieve a request media file.
     * 
     * @param recipeId - Recipe id number.
     * @param file     - File name.
     * @return - Recipe media file.
     * @throws SQLException
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/retrieverecipemedia/{recipeId}/{file}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> retrieverecipemedia(@PathVariable String recipeId, @PathVariable String file)
            throws SQLException, FileNotFoundException {
        File requestFile = new File(configDataAccess.getConfig("FILESHARE_PATH") + "\\" + recipeId + "\\" + file);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(requestFile));
        HttpHeaders headers = new HttpHeaders();
        String mediaType = "image/jpeg";
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", requestFile.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(requestFile.length())
                .contentType(MediaType.parseMediaType(mediaType)).body(resource);
        return responseEntity;
    }

    /**
     * retrieverecipemedianames - API call to retrieve a list of recipe media file
     * names for recipe id.
     * 
     * @param recipeId - Recipe id number.
     * @return - List of media file names.
     * @throws SQLException
     */
    @RequestMapping(value = "/retrieverecipemedianames/{recipeId}", method = RequestMethod.GET)
    public List<String> retrieverecipemedianames(@PathVariable String recipeId) throws SQLException {
        String dbConfig = configDataAccess.getConfig("FILESHARE_PATH") + "\\" + recipeId;
        List<String> result = new ArrayList<String>();
        if (new File(dbConfig).exists() && (new File(dbConfig).listFiles()).length > 0) {
            for (File file : new File(dbConfig).listFiles()) {
                if (file.getName().toUpperCase().contains(".JPEG") || file.getName().toUpperCase().contains(".JPG")
                        || file.getName().toUpperCase().contains(".MP4")) {
                    result.add(file.getName());
                }
            }
        }
        if (result.size() == 0) {
            result.add("DEFAULT.JPG");
        }
        return result;
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
