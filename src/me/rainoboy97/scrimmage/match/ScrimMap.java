package me.rainoboy97.scrimmage.match;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class ScrimMap {

	String dirname;
	File origdir;
	String ymlname;
	FileConfiguration yml;
	Integer playercount;

	public ScrimMap(String filename) {
		dirname = filename;
		origdir = new File(Bukkit.getWorldContainer() + "\\" + dirname);
		yml = YamlConfiguration.loadConfiguration(new File(Bukkit
				.getWorldContainer() + "\\" + dirname + "\\map.yml"));
		ymlname = yml.getString("map.name");
		playercount = yml.getInt("map.players");
	}

	public String getDirName() {
		return dirname;
	}

	public String getDisplayName() {
		return ymlname;
	}

	public File getOrigDir() {
		return origdir;
	}

	public FileConfiguration getYaml() {
		return yml;
	}

	public Integer getPlayerCount() {
		return playercount;
	}

}