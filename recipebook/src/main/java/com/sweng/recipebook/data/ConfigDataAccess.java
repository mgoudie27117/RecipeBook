package com.sweng.recipebook.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ConfigDataAccess - DataAccess class for application configuration options.
 */
public class ConfigDataAccess extends DataAccess {

    public ConfigDataAccess() {
        super();
    }

    /**
     * getConfig - Method to retrieve a configuration variable based on the
     * parameter.
     * 
     * @param configName - Configuration name.
     * @return - Configuration variable if found, or an empty string otherwise.
     * @throws SQLException
     */
    public String getConfig(String configName) throws SQLException {
        String result = "";
        String query = "SELECT config_variable FROM recipebook_config WHERE config_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setString(1, configName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("config_variable");
            }
        } finally {
            statement.close();
        }
        return result;
    }
}
