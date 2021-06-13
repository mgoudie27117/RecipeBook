package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.models.Measure;
import com.sweng.recipebook.models.MeasureType;
import com.sweng.recipebook.models.RecipeMeasure;

import org.junit.jupiter.api.Test;

/**
 * MeasureTest - Test class for the Measure derived classes.
 */
public class MeasureTest {

    /**
     * measureObjectGetterTest - Test method for verifying getter on derived Measure
     * classes.
     * 
     * Related Test Case Number(s): T18
     */
    @Test
    public void measureObjectGetterTest() {
        Measure measureTest1 = new RecipeMeasure();
        assertEquals(measureTest1.getMeasureId(), 0);
        assertEquals(measureTest1.getMeasureUnit(), "");
        assertEquals(measureTest1.getMeasureType(), MeasureType.NONE);

        Measure measureTest2 = new RecipeMeasure("TEST");
        assertEquals(measureTest2.getMeasureId(), 0);
        assertEquals(measureTest2.getMeasureUnit(), "TEST");
        assertEquals(measureTest2.getMeasureType(), MeasureType.NONE);

        Measure measureTest3 = new RecipeMeasure(MeasureType.RECIPE);
        assertEquals(measureTest3.getMeasureId(), 0);
        assertEquals(measureTest3.getMeasureUnit(), "");
        assertEquals(measureTest3.getMeasureType(), MeasureType.RECIPE);

        Measure measureTest4 = new RecipeMeasure(1, "TEST", MeasureType.RECIPE);
        assertEquals(measureTest4.getMeasureId(), 1);
        assertEquals(measureTest4.getMeasureUnit(), "TEST");
        assertEquals(measureTest4.getMeasureType(), MeasureType.RECIPE);
    }
}
