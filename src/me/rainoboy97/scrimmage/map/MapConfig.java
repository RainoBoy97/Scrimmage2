package me.rainoboy97.scrimmage.map;

import java.io.File;

import me.rainoboy97.scrimmage.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class MapConfig {

	private File path;
	private FileConfiguration map;
	
	public String mapname;
	
	public Location observerspawn;
	public Location redspawn;
	public Location bluespawn;
	
	public ItemStack[] redkit;
	public ItemStack[] bluekit;
	
	public MapConfig(String mapname) {
		this.mapname = mapname;
		this.path = new File(Bukkit.getWorldContainer() + mapname + File.separator + "map.yml");
		map = YamlConfiguration.loadConfiguration(path);
		this.load();
	}
	
	private void load() {
		this.observerspawn = LocationUtils.stringToLoc(mapname + map.getString("spawns.observer"));
		this.redspawn = LocationUtils.stringToLoc(mapname + map.getString("spawns.red"));
		this.bluespawn = LocationUtils.stringToLoc(mapname + map.getString("spawns.blue"));
	}
	
}
