package com.sweng.recipebook.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sweng.recipebook.models.Measure;
import com.sweng.recipebook.models.MeasureType;
import com.sweng.recipebook.models.RecipeMeasure;

/**
 * MeasureDataAccess - Data access class for application measure units.
 */
public class MeasureDataAccess extends DataAccess {

    public MeasureDataAccess() {
        super();
    }

    /**
     * getRecipeMeasure - Method to query measure units of a type indicated by the
     * parameters.
     * 
     * @param type - MeasureType enum requested.
     * @return - ArrayList of Measures of the requested type.
     * @throws SQLException
     */
    public ArrayList<Measure> getMeasure(MeasureType type) throws SQLException {
        ArrayList<Measure> result = new ArrayList<Measure>();
        String query = "SELECT measure_unit_id, measure_unit FROM recipebook_measure_unit WHERE measure_type = ? ORDER BY measure_unit ASC";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setString(1, type.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new RecipeMeasure(resultSet.getInt("measure_unit_id"), resultSet.getString("measure_unit"),
                        type));
            }
        } finally {
            statement.close();
        }
        return result;
    }
}
