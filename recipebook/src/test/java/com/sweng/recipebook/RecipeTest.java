package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.models.Ingredient;
import com.sweng.recipebook.models.IngredientComposite;
import com.sweng.recipebook.models.Recipe;
import com.sweng.recipebook.models.RecipeIngredient;
import com.sweng.recipebook.models.RecipeMediaComposite;
import com.sweng.recipebook.models.ReviewComposite;
import com.sweng.recipebook.models.SharedRecipe;

import org.junit.jupiter.api.Test;

/**
 * RecipeTest - Test class for the Recipe derived classes.
 */
public class RecipeTest {

        /**
         * recipeObjectGettersTest - Test method for verifying derived Recipe getters.
         * 
         * Related Test Case Number(s): T21
         */
        @Test
        public void recipeObjectGettersTest() {
                IngredientComposite compositeTest = new IngredientComposite();
                compositeTest.addIngredient(new RecipeIngredient());
                Ingredient[] arrayTest = new RecipeIngredient[] { new RecipeIngredient() };

                Recipe recipeTest1 = new SharedRecipe();
                assertEquals(recipeTest1.getRecipeId(), 0);
                assertEquals(recipeTest1.getRecipeName(), "");
                assertEquals(recipeTest1.getRecipeDescription(), "");
                assertEquals(recipeTest1.getServingSize(), 0);
                assertEquals(recipeTest1.getInstructions(), "");
                assertEquals(recipeTest1.getIngredients().size(), 0);

                Recipe recipeTest2 = new SharedRecipe("TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS");
                assertEquals(recipeTest2.getRecipeId(), 0);
                assertEquals(recipeTest2.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest2.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest2.getServingSize(), 1);
                assertEquals(recipeTest2.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest2.getIngredients().size(), 0);

                Recipe recipeTest3 = new SharedRecipe("TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                compositeTest);
                assertEquals(recipeTest3.getRecipeId(), 0);
                assertEquals(recipeTest3.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest3.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest3.getServingSize(), 1);
                assertEquals(recipeTest3.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest3.getIngredients().size(), 1);

                Recipe recipeTest4 = new SharedRecipe(1, "TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                compositeTest);
                assertEquals(recipeTest4.getRecipeId(), 1);
                assertEquals(recipeTest4.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest4.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest4.getServingSize(), 1);
                assertEquals(recipeTest4.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest4.getIngredients().size(), 1);

                Recipe recipeTest5 = new SharedRecipe("TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                arrayTest);
                assertEquals(recipeTest5.getRecipeId(), 0);
                assertEquals(recipeTest5.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest5.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest5.getServingSize(), 1);
                assertEquals(recipeTest5.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest5.getIngredients().size(), 1);

                Recipe recipeTest6 = new SharedRecipe(1, "TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                new IngredientComposite(), new RecipeMediaComposite());
                assertEquals(recipeTest6.getRecipeId(), 1);
                assertEquals(recipeTest6.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest6.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest6.getServingSize(), 1);
                assertEquals(recipeTest6.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest6.getIngredients().size(), 0);

                Recipe recipeTest7 = new SharedRecipe(1, "TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                new IngredientComposite(), new RecipeMediaComposite(), new ReviewComposite());
                assertEquals(recipeTest7.getRecipeId(), 1);
                assertEquals(recipeTest7.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest7.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest7.getServingSize(), 1);
                assertEquals(recipeTest7.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest7.getIngredients().size(), 0);
                assertEquals(recipeTest7.getRecipeMedia().size(), 0);
                assertEquals(recipeTest7.getReviews().size(), 0);

                Recipe recipeTest8 = new SharedRecipe(1, "TEST_NAME");
                assertEquals(recipeTest8.getRecipeId(), 1);
                assertEquals(recipeTest8.getRecipeName(), "TEST_NAME");

                Recipe recipeTest9 = new SharedRecipe(1, "TEST_NAME", "TEST_DESCRIPTION", 1, "TEST_INSTRUCTIONS",
                                new IngredientComposite(), new RecipeMediaComposite(), "TEST", 1);
                assertEquals(recipeTest9.getRecipeId(), 1);
                assertEquals(recipeTest9.getRecipeName(), "TEST_NAME");
                assertEquals(recipeTest9.getRecipeDescription(), "TEST_DESCRIPTION");
                assertEquals(recipeTest9.getServingSize(), 1);
                assertEquals(recipeTest9.getInstructions(), "TEST_INSTRUCTIONS");
                assertEquals(recipeTest9.getIngredients().size(), 0);
                assertEquals(recipeTest9.getRecipeMedia().size(), 0);
        }
}
