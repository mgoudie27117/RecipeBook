package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.models.Ingredient;
import com.sweng.recipebook.models.IngredientComposite;
import com.sweng.recipebook.models.RecipeIngredient;

import org.junit.jupiter.api.Test;

/**
 * IngredientTest - Test class for the Ingredient derived classes.
 */
public class IngredientTest {

    /**
     * ingredientObjectGettersSettersTest - Test method for verifying derived
     * Ingredient getters and mutators.
     * 
     * Related Test Case Number(s): T19
     */
    @Test
    public void ingredientObjectGettersSettersTest() {
        Ingredient ingredientTest1 = new RecipeIngredient();
        assertEquals(ingredientTest1.getIngredientId(), 0);
        assertEquals(ingredientTest1.getIngredientName(), "");
        assertEquals(ingredientTest1.getPortionAmount(), 0.0);
        assertEquals(ingredientTest1.getPortionMeasure(), "");
        ingredientTest1.setPortionAmount(1.01);
        ingredientTest1.setPortionMeasure("cup");
        assertEquals(ingredientTest1.getPortionAmount(), 1.01);
        assertEquals(ingredientTest1.getPortionMeasure(), "cup");

        Ingredient ingredientTest2 = new RecipeIngredient("TEST_NAME", 1.01, "cup");
        assertEquals(ingredientTest2.getIngredientId(), 0);
        assertEquals(ingredientTest2.getIngredientName(), "TEST_NAME");
        assertEquals(ingredientTest2.getPortionAmount(), 1.01);
        assertEquals(ingredientTest2.getPortionMeasure(), "cup");

        Ingredient ingredientTest3 = new RecipeIngredient(1, "TEST_NAME", 1.01, "cup");
        assertEquals(ingredientTest3.getIngredientId(), 1);
        assertEquals(ingredientTest3.getIngredientName(), "TEST_NAME");
        assertEquals(ingredientTest3.getPortionAmount(), 1.01);
        assertEquals(ingredientTest3.getPortionMeasure(), "cup");
    }

    /**
     * ingredientCompositeTest - Test method for verifying IngredientComposite
     * methods.
     * 
     * Related Test Case Number(s): T20
     */
    @Test
    public void ingredientCompositeTest() {
        IngredientComposite compositeTest = new IngredientComposite();
        assertEquals(compositeTest.getIngredients().size(), 0);
        compositeTest.addIngredient(new RecipeIngredient());
        assertEquals(compositeTest.getIngredients().size(), 1);
        compositeTest.removeIngredient(compositeTest.getIngredients().get(0));
        assertEquals(compositeTest.getIngredients().size(), 0);
    }
}
