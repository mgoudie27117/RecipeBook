package com.sweng.recipebook.controller;

import com.sweng.recipebook.data.DataAccessConcreteCreator;
import com.sweng.recipebook.data.UserDataAccess;
import com.sweng.recipebook.models.RecipeBookUser;
import com.sweng.recipebook.models.User;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController - REST controller for all API calls related to application
 * users.
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends Controller {

	private UserDataAccess userDataAccess = (UserDataAccess) new DataAccessConcreteCreator().createDataAccess("user");

	/**
	 * createuser - API call to create a user for the application and add to the
	 * database.
	 * 
	 * @param payload - JSON object passed in request body containing user
	 *                information.
	 * @return - RecipeBookUser object of the created user.
	 */
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public User createuser(@RequestBody Map<String, String> payload) throws SQLException {
		if (!payload.isEmpty()) {
			User result = userDataAccess.createUser(payload.get("firstName"), payload.get("lastName"),
					payload.get("password"), payload.get("userName"));
			return result;
		}
		return new RecipeBookUser();
	}

	/**
	 * login - API call to peform login functionality for the passed user.
	 * 
	 * @param payload - JSON object passed in request body containing user
	 *                information.
	 * @throws SQLException
	 * @return- RecipeBookUser object of the login result user.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody Map<String, String> payload) throws SQLException {
		if (!payload.isEmpty()) {
			User result = userDataAccess.validateUser(payload.get("password"), payload.get("userName"));
			if (result.getAuthenticated()) {
				result.setAccessToken(JWT.generateToken(result));
			}
			return result;
		}
		return new RecipeBookUser();
	}

	/**
	 * usernameexists - API call to verify the existence of a duplicate username for
	 * the application.
	 * 
	 * @param userName - The username passed as a URL parameter.
	 * @return True if the username already exists, otherwise false.
	 * @throws SQLException
	 */
	@RequestMapping(value = "/usernameexists", method = RequestMethod.GET)
	public Boolean usernameexists(@RequestParam String userName) throws SQLException {
		return (userName.length() > 0) ? userDataAccess.verifyDuplicateUsername(userName) : false;
	}
}