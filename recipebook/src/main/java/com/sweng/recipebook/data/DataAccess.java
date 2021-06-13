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
}
