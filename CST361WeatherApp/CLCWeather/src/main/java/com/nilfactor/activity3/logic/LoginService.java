package com.nilfactor.activity3.logic;

import com.nilfactor.activity3.model.User;

public class LoginService {
	public static boolean isValidLogin(User user, String username, String password) {
		// username must match, password must match
		return username.equals(user.getUsername())
			&& password.equals(user.getPassword());
	}
}
