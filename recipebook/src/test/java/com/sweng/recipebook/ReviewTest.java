package com.sweng.recipebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sweng.recipebook.models.RecipeReview;
import com.sweng.recipebook.models.Review;
import com.sweng.recipebook.models.ReviewComposite;

import org.junit.jupiter.api.Test;

/**
 * ReviewTest - Test class for the Review derived classes.
 */
public class ReviewTest {

    /**
     * reviewCompositeTest - Test method for verifying derived ReviewComposite
     * object.
     * 
     * Related Test Case Number(s): T43
     */
    @Test
    public void reviewCompositeTest() {
        ReviewComposite compositeTest = new ReviewComposite();
        Review testReview = new RecipeReview();
        compositeTest.addReview(testReview);
        assertEquals(compositeTest.getReviews().size(), 1);
        assertEquals(compositeTest.getReviews().get(0).getCritic(), "");
        assertEquals(compositeTest.getReviews().get(0).getRating(), 0);
        assertEquals(compositeTest.getReviews().get(0).getComments(), "");
        compositeTest.removeReview(testReview);
        assertEquals(compositeTest.getReviews().size(), 0);
    }

    /**
     * reviewObjectTest - Test method for verifying derived RecipeReview object.
     * 
     * Related Test Case Number(s): T44
     */
    @Test
    public void reviewObjectTest() {
        Review testReview = new RecipeReview("TEST_CRITIC", 5, "TEST_COMMENT");
        assertEquals(testReview.getCritic(), "TEST_CRITIC");
        assertEquals(testReview.getRating(), 5);
        assertEquals(testReview.getComments(), "TEST_COMMENT");
    }
}
