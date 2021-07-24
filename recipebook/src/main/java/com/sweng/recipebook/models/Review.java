package com.sweng.recipebook.models;

/**
 * Review - Abstract class for review classes to extend.
 */
public abstract class Review {

    protected String critic;
    protected int rating;
    protected String comments;

    // region Constructors
    public Review() {
        this.critic = "";
        this.rating = 0;
        this.comments = "";
    }

    public Review(String critic, int rating, String comments) {
        this.critic = critic;
        this.rating = rating;
        this.comments = comments;
    }
    // end region

    /**
     * getCritic - Getter for critic variable.
     * 
     * @return - Critic name string.
     */
    public String getCritic() {
        return this.critic;
    }

    /**
     * getRating - Getter for rating variable.
     * 
     * @return - Rating int.
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * getComments - Getter for comments variable.
     * 
     * @return - Comments string.
     */
    public String getComments() {
        return this.comments;
    }
}
