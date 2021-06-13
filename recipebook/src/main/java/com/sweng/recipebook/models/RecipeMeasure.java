package com.sweng.recipebook.models;

/**
 * RecipeMeasure - Class to extend Measure abstract.
 */
public class RecipeMeasure extends Measure {

    // region Constructors
    public RecipeMeasure() {
        super();
    }

    public RecipeMeasure(String measureUnit) {
        super(measureUnit);
    }

    public RecipeMeasure(MeasureType measureType) {
        super(measureType);
    }

    public RecipeMeasure(int measureId, String measureUnit, MeasureType measureType) {
        super(measureId, measureUnit, measureType);
    }
    // end region
}
