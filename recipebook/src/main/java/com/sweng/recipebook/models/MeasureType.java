package com.sweng.recipebook.models;

/**
 * MeasureType - Enumeration of measurement types.
 */
public enum MeasureType {
    RECIPE {
        public String toString() {
            return "recipe";
        }
    },
    NUTRITIONAL {
        public String toString() {
            return "nutritional";
        }
    },
    NONE {
        public String toString() {
            return "-";
        }
    }

}
