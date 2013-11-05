package me.rainoboy97.scrimmage.handlers;

import java.util.HashMap;
import java.util.Map;

import me.rainoboy97.scrimmage.events.ScrimSetMatchEvent;
import me.rainoboy97.scrimmage.match.ScrimMatch;

import org.bukkit.Bukkit;
import org.bukkit.Location;

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

	public static Location getCurrentTeleport() {
		return getCurrentMatch().getObsSpawn();
	}

	public static ScrimMatch getPrevMatch() {
		if (matches.containsKey(current - 1))
			return matches.get(current - 1);
		else
			return null;
	}

	public static ScrimMatch getCurrentMatch() {
		return matches.get(current);
	}

	public static void setMatch(String mn) {
		ScrimMatch sm = new ScrimMatch(next, ScrimMapHandler.get(mn));
		matches.put(next, sm);
		current = next;
		next = current + 1;
		Bukkit.getPluginManager().callEvent(
				new ScrimSetMatchEvent(mn, sm, getPrevMatch()));
	}

	public static void setMatch(ScrimMatch m) {
		matches.put(next, m);
		current = next;
		next = current + 1;
		Bukkit.getPluginManager().callEvent(
				new ScrimSetMatchEvent(m.getMapName(), m, getPrevMatch()));
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