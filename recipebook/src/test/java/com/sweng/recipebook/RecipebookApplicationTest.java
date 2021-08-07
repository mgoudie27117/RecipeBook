package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.data.DataAccess;
import com.sweng.recipebook.data.DataAccessConcreteCreator;

import org.junit.jupiter.api.Test;

/**
 * RecipebookApplicationTest - Test class for the RecipebookApplication class.
 */
public class RecipebookApplicationTest {

    /**
     * applicationEntryTest - Test application entry.
     */
    // @Test
    public void applicationEntryTest() {
        RecipebookApplication.main(new String[] {});
    }

    /**
     * defaultDataAccess - Test to verify default DataAccess creator.
     */
    // @Test
    public void defaultDataAccess() {
        DataAccess access = new DataAccessConcreteCreator().createDataAccess("");
        assertEquals(null, access);
    }
}
