package com.nilfactor.activity3.logic;

import javax.interceptor.Interceptors;

import com.nilfactor.activity3.model.User;
import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;

@Interceptors(LogInterceptor.class)
public class LoginService {
	@Interceptors(LogInterceptor.class)
	public static boolean isValidLogin(User user, String username, String password) {
		// username must match, password must match
		ServiceService.getLogger("LoginService").debug(username + " " + " with password " + password + " has attempted to login");
		return username.equals(user.getUsername())
			&& password.equals(user.getPassword());
	}
}
