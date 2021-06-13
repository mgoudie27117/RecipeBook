package com.sweng.recipebook.controller;

import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.data.MeasureDataAccess;
import com.sweng.recipebook.models.Measure;
import com.sweng.recipebook.models.MeasureType;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MeasureController - REST controller for all API calls related to application
 * measure unit.
 */
@RestController
@RequestMapping("/api/measure")
public class MeasureController extends Controller {

    private MeasureDataAccess measureDataAccess = (MeasureDataAccess) new DataAccessConcreteCreator()
            .createDataAccess("measure");

    /**
     * recipemeasures - API call to return measure units related to recipe
     * ingredients.
     * 
     * @return - ArrayList of recipe measure units.
     * @throws SQLException
     */
    @RequestMapping(value = "/recipemeasures", method = RequestMethod.GET)
    public ArrayList<Measure> recipemeasures() throws SQLException {
        return measureDataAccess.getMeasure(MeasureType.RECIPE);
    }
}
