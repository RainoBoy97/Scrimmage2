package me.rainoboy97.scrimmage.handlers;

import java.util.HashMap;
import java.util.Map;

import me.rainoboy97.scrimmage.match.ScrimMatch;

public class ScrimMatchHandler {

	static Integer current;
	static Integer next;
	static Map<Integer, ScrimMatch> matches = new HashMap<Integer, ScrimMatch>();

	public static void initHandler() {
		current = -1;
		next = 0;
	}

	public static Integer getCurrentId() {
		return current;
	}

	public static Integer getNextId() {
		return next;
	}

	public static ScrimMatch getCurrentMatch() {
		return matches.get(current);
	}

	public static void setMatch(String mn) {
		ScrimMatch sm = new ScrimMatch(next, ScrimMapHandler.get(mn));
		matches.put(next, sm);
		current = next;
		next = current + 1;
	}

	public static boolean isRunning() {
		if (current == -1)
			return false;
		else {
			switch (getCurrentMatch().getMatchState()) {
			case RUNNING:
				return true;
			default:
				return false;
			}
		}
	}

}