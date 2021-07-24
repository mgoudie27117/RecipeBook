package com.sweng.recipebook.data;

/**
 * DataAccessCreator - Abstract class for factory to extend from.
 */
public abstract class DataAccessCreator {

    public DataAccessCreator() {
    }

    /**
     * createDataAccess - Method to return a derived DataAccess object based on the
     * parameter indicator.
     * 
     * @param access - Access string indicator.
     * @return DataAccess object.
     */
    public DataAccess createDataAccess(String access) {
        switch (access) {
            case "user":
                return new UserDataAccess();
            case "recipe":
                return new RecipeDataAccess();
            case "ingredient":
                return new IngredientDataAccess();
            case "measure":
                return new MeasureDataAccess();
            case "config":
                return new ConfigDataAccess();
            case "review":
                return new ReviewDataAccess();
            default:
                return null;
        }
    }
}
