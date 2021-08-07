package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.models.MeasureType;

import org.junit.jupiter.api.Test;

/**
 * MeasureTypeTest - Test class for the MeasureType Enum.
 */
public class MeasureTypeTest {

    /**
     * measureTypeToStringTest - Test method for verify enum toString results.
     * 
     * Related Test Case Number(s): T17
     */
    // @Test
    public void measureTypeToStringTest() {
        assertEquals(MeasureType.RECIPE.toString(), "recipe");
        assertEquals(MeasureType.NUTRITIONAL.toString(), "nutritional");
        assertEquals(MeasureType.NONE.toString(), "-");
    }
}
