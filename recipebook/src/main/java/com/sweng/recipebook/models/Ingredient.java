package com.sweng.recipebook.models;

/**
 * Ingredient - Abstract class for ingredient classes to extend.
 */
public abstract class Ingredient {
    protected int ingredientId;
    protected String ingredientName;
    protected double portionAmount;
    protected String portionMeasure;

    // region Constructors
    public Ingredient() {
        this.ingredientId = 0;
        this.ingredientName = "";
        this.portionAmount = 0.0;
        this.portionMeasure = "";
    }

    public Ingredient(String ingredientName, double portionAmount, String portionMeasure) {
        this.ingredientId = 0;
        this.ingredientName = ingredientName;
        this.portionAmount = portionAmount;
        this.portionMeasure = portionMeasure;
    }

    public Ingredient(int ingredientId, String ingredientName, double portionAmount, String portionMeasure) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.portionAmount = portionAmount;
        this.portionMeasure = portionMeasure;
    }
    // end region

    /**
     * getIngredientId - Getter for ingredient's id number.
     * 
     * @return - Ingredient id int.
     */
    public int getIngredientId() {
        return this.ingredientId;
    }

    /**
     * getIngredientName - Getter for ingredient's name.
     * 
     * @return - Ingredient name String.
     */
    public String getIngredientName() {
        return this.ingredientName;
    }

    /**
     * getPortionAmount - Getter for the ingredient portion amount.
     * 
     * @return - Portion amount double.
     */
    public double getPortionAmount() {
        return this.portionAmount;
    }

    /**
     * getPortionMeasure - Getter for a portion's measure.
     * 
     * @return - Portion Measure.
     */
    public String getPortionMeasure() {
        return this.portionMeasure;
    }

    /**
     * setPortionAmount - Mutator for ingredients portion amount.
     * 
     * @param portionAmount - New double portion amount.
     */
    public void setPortionAmount(double portionAmount) {
        this.portionAmount = portionAmount;
    }

    /**
     * setPortionMeasure - Mutator for portion's measure.
     * 
     * @param portionMeasure - New Measure for portion.
     */
    public void setPortionMeasure(String portionMeasure) {
        this.portionMeasure = portionMeasure;
    }
}
