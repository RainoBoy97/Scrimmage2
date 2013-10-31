package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.io.IOException;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.events.ScrimMapCopyEvent;
import me.rainoboy97.scrimmage.utils.FileUtils;
import me.rainoboy97.scrimmage.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ScrimMap {

	String name;
	File mapdir;

	String stringname;
	FileConfiguration yml;
	Location obs_spawn;
	Location red_spawn;
	Location blue_spawn;
	World mapworld;
	Location[] playable;

	public ScrimMap(String filename) {
		name = filename;
		mapdir = new File(Bukkit.getWorldContainer() + "\\map_" + filename);
		yml = YamlConfiguration.loadConfiguration(new File(Bukkit.getWorldContainer() + "\\map_" + filename + "\\map.yml"));
		obs_spawn = LocationUtils.stringToLoc(yml, world)
	}

	public void prepare(int folderid) {
		File findir = new File(Bukkit.getWorldContainer() + "\\match-"
				+ folderid);
		try {
			FileUtils.copyFolder(mapdir, findir);
			Bukkit.getPluginManager().callEvent(
					new ScrimMapCopyEvent(this, mapdir, findir, name, true));
		} catch (IOException e) {
			ScrimLogger.severe("Could not prepare map for loading!");
			Bukkit.getPluginManager().callEvent(
					new ScrimMapCopyEvent(this, mapdir, findir, name, false));
			e.printStackTrace();
		}
		mapworld = Bukkit.getWorld("map_" + name);
	}

	public void load() {
		Bukkit.getServer().getWorlds().add(mapworld);
	}

}