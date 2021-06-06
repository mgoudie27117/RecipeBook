package com.sweng.recipebook.controller;

import com.sweng.recipebook.models.RecipeBookUser;
import com.sweng.recipebook.security.JWTHandler;

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
public class UserController {

	private static final JWTHandler JWT = new JWTHandler();

	/**
	 * createuser - API call to create a user for the application and add to the
	 * database.
	 * 
	 * @param payload - JSON object passed in request body containing user
	 *                information.
	 * @return - RecipeBookUser object of the created user.
	 */
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public RecipeBookUser createuser(@RequestBody Map<String, String> payload) {
		RecipeBookUser result = new RecipeBookUser();
		if (!payload.isEmpty()) {
			result = new RecipeBookUser(payload.get("firstName"), payload.get("lastName"), payload.get("password"), 0,
					payload.get("userName"));
			result.createUser();
		}
		return result;
	}

	/**
	 * login - API call to peform login functionality for the passed user.
	 * 
	 * @param payload - JSON object passed in request body containing user
	 *                information.
	 * @return- RecipeBookUser object of the login result user.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RecipeBookUser login(@RequestBody Map<String, String> payload) {
		RecipeBookUser result = null;
		if (!payload.isEmpty()) {
			result = new RecipeBookUser(payload.get("password"), payload.get("userName"));
			result.login();
			if (result.getAuthenticated()) {
				result.setAccessToken(JWT.generateToken(result));
			}
		}
		return result;
	}

	/**
	 * usernameexists - API call to verify the existence of a duplicate username for
	 * the application.
	 * 
	 * @param userName - The username passed as a URL parameter.
	 * @return True if the username already exists, otherwise false.
	 */
	@RequestMapping(value = "/usernameexists", method = RequestMethod.GET)
	public Boolean usernameexists(@RequestParam String userName) {
		return (userName.length() > 0) ? new RecipeBookUser("", userName).verifyDuplicateUsername() : false;
	}
}