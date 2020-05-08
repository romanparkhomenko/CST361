package com.nilfactor.activity3.userFactory;

import java.security.SecureRandom;
import java.util.Random;

public class RandomPassword implements RandomInfo {
	
	public String generate() {
		Random rand = new Random();
		int min = 10;
		int max = 25;
		int randoms = (int) (Math.random()*(max-min+1)+min);

		// ASCII range
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*abcdefghijklmnopqrstuvwxyz";

		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();

		// each iteration of loop choose a character randomly from the given ASCII range
		// and append it to StringBuilder instance

		for (int i = 0; i < randoms; i++) {
			int randomIndex = random.nextInt(chars.length());
			sb.append(chars.charAt(randomIndex));
		}
		return sb.toString();
	}
	
}

