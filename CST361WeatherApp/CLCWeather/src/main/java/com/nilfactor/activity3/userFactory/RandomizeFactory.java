package com.nilfactor.activity3.userFactory;

public class RandomizeFactory {
	public RandomInfo getRandRequest(String request) {
		if (request == null) {
			return null;
		}
		if(request.equalsIgnoreCase("NAME") || request.equalsIgnoreCase("USERNAME")) {
			return new RandomUsername();
		} else if (request.equalsIgnoreCase("PASSWORD")){
			return new RandomPassword();
		}
		return null;
	}
}
