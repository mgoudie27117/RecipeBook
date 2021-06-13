package com.sweng.recipebook.models;

/**
 * Measure - Abstract method to define application measure.
 */
public abstract class Measure {
    protected int measureId;
    protected String measureUnit;
    protected MeasureType measureType;

    // region Constructors
    public Measure() {
        this.measureId = 0;
        this.measureUnit = "";
        this.measureType = MeasureType.NONE;
    }

    public Measure(String measureUnit) {
        this.measureId = 0;
        this.measureUnit = measureUnit;
        this.measureType = MeasureType.NONE;
    }

    public Measure(MeasureType measureType) {
        this.measureId = 0;
        this.measureUnit = "";
        this.measureType = measureType;
    }

    public Measure(int measureId, String measureUnit, MeasureType measureType) {
        this.measureId = measureId;
        this.measureUnit = measureUnit;
        this.measureType = measureType;
    }
    // end region

    /**
     * getMeasureId - Getter for measure id.
     * 
     * @return Measure id int.
     */
    public int getMeasureId() {
        return this.measureId;
    }

    /**
     * getMeasureUnit - Getter for unit of measurement.
     * 
     * @return - Unit of measure.
     */
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    /**
     * getMeasureType - Getter for measurement type.
     * 
     * @return - MeasureType enum.
     */
    public MeasureType getMeasureType() {
        return this.measureType;
    }
}
