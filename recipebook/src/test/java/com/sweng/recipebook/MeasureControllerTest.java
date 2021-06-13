package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sweng.recipebook.controller.MeasureController;
import com.sweng.recipebook.models.Measure;

import org.junit.jupiter.api.Test;

/**
 * MeasureControllerTest - Test class for the MeasureController class.
 */
public class MeasureControllerTest {

    private MeasureController controllerTest = new MeasureController();

    /**
     * recipeMeasuresTest - Test method for MeasureController recipeMeasures
     * functionality.
     * 
     * Related Test Case Number(s): T15
     * 
     * @throws SQLException
     */
    @Test
    public void recipeMeasuresTest() throws SQLException {
        ArrayList<Measure> testMeasures = controllerTest.recipemeasures();
        assertEquals(testMeasures.size(), 20);
    }

}
