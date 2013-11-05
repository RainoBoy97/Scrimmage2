package me.rainoboy97.scrimmage.utils;

import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

public class LookupUtils {

	public static int countTeam(String s) {
		int count = 0;
		if ("blue".toLowerCase().startsWith(s.toLowerCase())
				|| "blue".toLowerCase().equals(s.toLowerCase())) {
			count++;
		}
		if ("red".toLowerCase().startsWith(s.toLowerCase())
				|| "red".toLowerCase().equals(s.toLowerCase())) {
			count++;
		}
		if ("observer".toLowerCase().startsWith(s.toLowerCase())
				|| "observer".toLowerCase().equals(s.toLowerCase())) {
			count++;
		}
		return count;
	}

	public static Team getTeam(String s) {
		Team t = Team.OBSERVER;
		if ("blue".toLowerCase().startsWith(s.toLowerCase())
				|| "blue".toLowerCase().equals(s.toLowerCase())) {
			t = Team.BLUE;
		}
		if ("red".toLowerCase().startsWith(s.toLowerCase())
				|| "red".toLowerCase().equals(s.toLowerCase())) {
			t = Team.RED;
		}
		if ("observer".toLowerCase().startsWith(s.toLowerCase())
				|| "observer".toLowerCase().equals(s.toLowerCase())) {
			t = Team.OBSERVER;
		}
		return t;
	}

}