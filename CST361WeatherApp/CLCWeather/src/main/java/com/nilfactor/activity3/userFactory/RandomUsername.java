package com.nilfactor.activity3.userFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUsername implements RandomInfo {
	private String genUserName;
	
	public String generate() {
		String[] words = {"Arrow","Assassin","Avenger","Blaze","Bodhi","Bolt","Boss","Captain","Crimson","Cyborg","Dark","Death",
				"Demon","Destructio","Doom","Doctor","Dragon","Enigma","Fallen","Fate","Flame","Frozen","Ghost","Great","Infinite","Immortal",
				"Killer","Knight","Lightning","Mist","Micro","Neo","Nightmare","Ninja","Outlaw","Prime","Realm","Seeker","Shadow","Silver","Sinister",
				"Slayer","Dniper","Trinity","Zephyr","Bulk","Digg","Undesired","Favored","Aluminium","Reveal","Tickly","Bloated","Servo","Quizzical","Burritos",
				"Against","Hold","Bumpkin","Gaps","Stinking","Stennack","Vegetarian","Jaguar","Gig","IttyBitty","Vaulting","Twit","Safehouse","Galaxy"};
		List<String> shuffleWords = Arrays.asList(words);
		Collections.shuffle(shuffleWords);
		shuffleWords.toArray(words);

		Random rand = new Random();

		int x = rand.nextInt(70);
		int y = rand.nextInt(70);
		int ending = rand.nextInt(10000);

		genUserName = words[x]+words[y]+ending;
		return genUserName;
	}
	
}
