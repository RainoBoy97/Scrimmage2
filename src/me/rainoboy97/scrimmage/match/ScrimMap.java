package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.io.IOException;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.events.ScrimMapCopyEvent;
import me.rainoboy97.scrimmage.utils.FileUtils;

import org.bukkit.Bukkit;

public class ScrimMap {

	int folderid;
	String name;
	File mapdir;

	public ScrimMap(int fid, String filename) {
		folderid = fid;
		name = filename;
		mapdir = new File(Bukkit.getWorldContainer() + "\\map_" + filename);
	}

	public void prepare() {
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
	}
	
	public void load() {
		
	}

}