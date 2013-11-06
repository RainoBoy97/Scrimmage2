package me.rainoboy97.scrimmage.utils;

import java.util.List;

import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler.Team;
import me.rainoboy97.scrimmage.match.ScrimMap;

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

	public static int countMap(String s) {
		int count = 0;
		List<ScrimMap> sm = ScrimMapHandler.getMapList();
		for (ScrimMap m : sm) {
			String dn = m.getDisplayName();
			if (dn.toLowerCase().startsWith(s.toLowerCase())) {
				count++;
			}
		}
		return count;
	}

	public static ScrimMap getMap(String s) {
		ScrimMap map = null;
		List<ScrimMap> sm = ScrimMapHandler.getMapList();
		for (ScrimMap m : sm) {
			String dn = m.getDisplayName();
			if (dn.toLowerCase().startsWith(s.toLowerCase())) {
				map = m;
				break;
			}
		}
		return map;
	}
}