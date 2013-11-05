package me.rainoboy97.scrimmage.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.match.ScrimMap;

import org.bukkit.Bukkit;

public final class ScrimMapHandler {

	private static Map<String, ScrimMap> maps = new HashMap<String, ScrimMap>();
	static Integer mapamount;

	public static void add(ScrimMap sm) {
		maps.put(sm.getDirName(), sm);
	}

	public static ScrimMap get(String n) {
		return maps.get(n);
	}

	public static ScrimMap getDefaultMap() {
		return (ScrimMap) maps.values().toArray()[0];
	}

	public static List<ScrimMap> getMapList() {
		List<ScrimMap> sm = new ArrayList<ScrimMap>();
		for (ScrimMap m : maps.values()) {
			sm.add(m);
		}
		return sm;
	}

	public static boolean loadMaps() {
		ScrimLogger.info("Loading maps...");
		File mapcont = Bukkit.getWorldContainer();
		for (File f : mapcont.listFiles()) {
			if (f.isDirectory() && f.getName().contains("map_")) {
				ScrimLogger.info("Detected map in directory " + f.getName()
						+ ". Loading...");
				File yml = new File(f.getAbsolutePath() + "\\map.yml");
				if (yml.exists()) {
					ScrimLogger
							.info("Configuration file exists. Load complete.");
					ScrimMap sm = new ScrimMap(f.getName());
					add(sm);
				} else {
					ScrimLogger
							.severe("Configuration file does not exist. Load aborted.");
				}
			}
		}
		mapamount = maps.size();
		if (mapamount > 0) {
			ScrimLogger.info("Finished loading " + mapamount + " maps!");
			return true;
		} else {
			ScrimLogger.alert("No maps could be loaded");
			ScrimLogger.alert("SERVER SHUTTING DOWN");
			return false;
		}

	}

	public static void clean() {
		maps.clear();
		mapamount = 0;
	}

}