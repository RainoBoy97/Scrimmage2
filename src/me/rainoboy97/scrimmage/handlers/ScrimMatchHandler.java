package me.rainoboy97.scrimmage.handlers;

import java.util.HashMap;
import java.util.Map;

import me.rainoboy97.scrimmage.events.ScrimSetMatchEvent;
import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.match.ScrimMatch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

	public static String getMotd() {
		ScrimMatch m = getCurrentMatch();
		switch (m.getMatchState()) {
		case STARTING:
			return ChatColor.GREEN + "» " + ChatColor.AQUA
					+ m.getMap().getDisplayName() + ChatColor.GREEN + " «";
		case RUNNING:
			return ChatColor.GOLD + "» " + ChatColor.AQUA
					+ m.getMap().getDisplayName() + ChatColor.GOLD + " «";
		case ENDED:
			return ChatColor.RED + "» " + ChatColor.AQUA
					+ m.getMap().getDisplayName() + ChatColor.RED + " «";
		default:
			return ChatColor.GRAY + "» " + ChatColor.AQUA
					+ m.getMap().getDisplayName() + ChatColor.GRAY + " «";
		}
	}

	public static Location getCurrentTeleport() {
		return getCurrentMatch().getObsSpawn();
	}

	public static ScrimMatch getPrevMatch() {
		if (matches.containsKey(current - 1))
			return matches.get(current - 1);
		else
			return matches.get(0);
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

	public static void setMatch(ScrimMap mn) {
		ScrimMatch sm = new ScrimMatch(next, mn);
		matches.put(next, sm);
		current = next;
		next = current + 1;
		Bukkit.getPluginManager().callEvent(
				new ScrimSetMatchEvent(mn.getDirName(), sm, getPrevMatch()));
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

	public static void clean() {
		if (getCurrentMatch() != null) {
			getCurrentMatch().end();
		}
		matches.clear();
		initHandler();
	}

}