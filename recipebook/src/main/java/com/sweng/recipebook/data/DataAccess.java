package com.sweng.recipebook.data;

import java.sql.Connection;

/**
 * DataAccess - Abstract class for database access classes to extend from.
 */
public abstract class DataAccess {
    protected static Connection connection;

    public DataAccess() {
        connection = DatabaseConnection.getConnection();
    }

    /**
     * correctJSONCharacters - Method to replace string characters from JSON passed
     * from message requests.
     * 
     * @param characters - String characters to be corrected.
     * @return - Corrected String.
     */
    protected String correctJSONCharacters(String characters) {
        return (characters.replaceAll("__", " ")).replaceAll("---", ",");
    }
}
