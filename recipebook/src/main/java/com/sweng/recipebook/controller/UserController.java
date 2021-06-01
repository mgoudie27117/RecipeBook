package com.sweng.recipebook.controller;

import com.sweng.recipebook.models.User;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody Map<String, String> payload) {
		User result = null;
		if (!payload.isEmpty() && payload.containsKey("userName") && payload.containsKey("password")) {
			String userName = payload.get("userName");
			String password = payload.get("password");
			result = new User(password, userName);
			result.login();
		}
		return result;
	}

	// NEW
	@RequestMapping(value = "/usernameexists", method = RequestMethod.GET)
	public Boolean usernameexists(@RequestParam String userName) {
		Boolean result = false;
		if (userName.length() > 0) {
			result = new User("", userName).verifyDuplicateUsername();
		}
		return result;
	}

	// NEW
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public User createuser(@RequestBody Map<String, String> payload) {
		User result = null;
		if (!payload.isEmpty() && payload.containsKey("userName") && payload.containsKey("password")
				&& payload.containsKey("firstName") && payload.containsKey("lastName")) {
			result = new User(payload.get("firstName"), payload.get("lastName"), payload.get("password"), 0,
					payload.get("userName"));
			result.createUser();
		}
		return result;
	}
}