package com.sweng.recipebook.models;

import java.util.ArrayList;

/**
 * ReviewComposite - Class that extends ReviewComposite to implement composite
 * pattern.
 */
public class ReviewComposite extends Review {
    ArrayList<Review> reviews;

    public ReviewComposite() {
        super();
        this.reviews = new ArrayList<Review>();
    }

    /**
     * addReview - Method to add a review to the composite.
     * 
     * @param review - Review to add.
     */
    public void addReview(Review review) {
        reviews.add(review);
    }

    /**
     * getReviews - Getter for composite reviews.
     * 
     * @return - ArrayList of reviews.
     */
    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    /**
     * removeReview - Method to remove a review from the composite.
     * 
     * @param review - Review to remove.
     */
    public void removeReview(Review review) {
        reviews.remove(review);
    }
}
