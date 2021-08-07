package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.sweng.recipebook.models.RecipeImage;
import com.sweng.recipebook.models.RecipeMedia;
import com.sweng.recipebook.models.RecipeMediaComposite;
import com.sweng.recipebook.models.RecipeMediaConcreteCreator;
import com.sweng.recipebook.models.RecipeVideo;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class RecipeMediaTest {

        /**
         * checkUserRecipeExistsAndShareTest - Test method for verify RecieMedia factory
         * object creation.
         * 
         * Related Test Case Number(s): T23
         */
        // @Test
        public void recipeMediaFactoryTest() {
                RecipeMedia recipeMediaTest1 = new RecipeMediaConcreteCreator().createRecipeMedia(
                                new MockMultipartFile("video", "TEST.mp4", "video/mp4", "MP4 TEST".getBytes()),
                                "\\TESTPATH");
                assertEquals(recipeMediaTest1.getClass(), RecipeVideo.class);

                RecipeMedia recipeMediaTest2 = new RecipeMediaConcreteCreator()
                                .createRecipeMedia(new MockMultipartFile("image", "TEST.jpeg",
                                                MediaType.IMAGE_JPEG_VALUE, "JPEG TEST".getBytes()), "\\TESTPATH");
                assertEquals(recipeMediaTest2.getClass(), RecipeImage.class);

                RecipeMedia recipeMediaTest3 = new RecipeMediaConcreteCreator().createRecipeMedia(new MockMultipartFile(
                                "image", "TEST.jpg", MediaType.IMAGE_JPEG_VALUE, "JPG TEST".getBytes()), "\\TESTPATH");
                assertEquals(recipeMediaTest3.getClass(), RecipeImage.class);

                RecipeMedia recipeMediaTest4 = new RecipeMediaConcreteCreator().createRecipeMedia(new MockMultipartFile(
                                "text", "TEST.txt", MediaType.TEXT_PLAIN_VALUE, "NULL TEST".getBytes()), "\\TESTPATH");
                assertEquals(recipeMediaTest4, null);

                RecipeMediaComposite composite = new RecipeMediaComposite();
                composite.addRecipeMedia(recipeMediaTest1);
                assertEquals(composite.getRecipeMedia().size(), 1);
                composite.removeRecipeMedia(recipeMediaTest1);
                assertEquals(composite.getRecipeMedia().size(), 0);
        }

        /**
         * recipeMediaObjectGetterTest - Test method for verify object getters
         * contructor and factory object creation.
         * 
         * Related Test Case Number(s): T22
         */
        // @Test
        public void recipeMediaObjectGetterTest() {
                RecipeMedia recipeMediaTest1 = new RecipeMediaConcreteCreator().createRecipeMedia(
                                new MockMultipartFile("video", "TEST.mp4", "video/mp4", "MP4 TEST".getBytes()),
                                "\\TESTPATH");
                assertEquals(recipeMediaTest1.getFile().getOriginalFilename(), "TEST.mp4");
                assertEquals(recipeMediaTest1.getStoragePath(), "\\TESTPATH");
                RecipeMedia recipeMediaTest2 = new RecipeImage();
                assertEquals(recipeMediaTest2.getFile(), null);
                assertEquals(recipeMediaTest2.getStoragePath(), "");
        }

        /**
         * checkUserRecipeExistsAndShareTest - Test method to verify RecipeMedia save
         * functionality.
         * 
         * Related Test Case Number(s): T24
         */
        // @Test
        public void recipeMediaSaveTest() throws FileNotFoundException, IOException {
                RecipeMedia recipeMediaTest1 = new RecipeMediaConcreteCreator().createRecipeMedia(
                                new MockMultipartFile("image", "TESTJpeg.jpeg", MediaType.IMAGE_JPEG_VALUE,
                                                IOUtils.toByteArray(new FileInputStream(
                                                                new File("C:\\DevFileShare\\TEST\\TESTjpeg.jpeg")))),
                                "C:\\DevFileShare\\TEST\\RESULT");
                recipeMediaTest1.save();
                File fileTest1 = new File("C:\\DevFileShare\\TEST\\RESULT\\TESTjpeg.jpeg");
                assertEquals(true, fileTest1.exists());
                if (fileTest1.exists()) {
                        fileTest1.delete();
                }
        }
}
