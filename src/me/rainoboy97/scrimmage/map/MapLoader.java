package me.rainoboy97.scrimmage.map;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class MapLoader {

	public static int loadMaps() {
		int loaded = 0;
		for (World w : Bukkit.getWorlds()) {
			File dir = new File(Bukkit.getWorldContainer() + File.separator + w.getName());
			if (new File(dir, "map.yml").exists()) {
				MapConfig config = new MapConfig(w.getName());
				MapHandler.addMap(w, config);
				loaded++;
			}
		}
		return loaded;
	}

}
