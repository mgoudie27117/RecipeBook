package com.sweng.recipebook.controller;

import com.sweng.recipebook.models.User;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestBody Map<String, String> payload) {
		User result = null;
		if (!payload.isEmpty()) {
			String userName = payload.get("userName");
			String password = payload.get("password");
			result = new User(password, userName);
			result.login();
		}
		return result;
	}
}