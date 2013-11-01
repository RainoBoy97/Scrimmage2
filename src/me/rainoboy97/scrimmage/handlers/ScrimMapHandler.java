package me.rainoboy97.scrimmage.handlers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.match.ScrimMap;

import org.bukkit.Bukkit;

public final class ScrimMapHandler {

	private static Map<String, ScrimMap> maps = new HashMap<String, ScrimMap>();
	static Integer mapamount = maps.size();

	public static void add(ScrimMap sm) {
		maps.put(sm.getDirName(), sm);
	}

	public static ScrimMap get(String n) {
		return maps.get(n);
	}

	public static void loadMaps() {
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
		ScrimLogger.info("Finished loading " + mapamount + " maps!");
	}
}