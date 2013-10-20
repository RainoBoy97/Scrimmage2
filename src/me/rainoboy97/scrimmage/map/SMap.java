package me.rainoboy97.scrimmage.map;

import lombok.Getter;
import lombok.Setter;
import me.rainoboy97.scrimmage.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class SMap {

	@Getter
	@Setter
	private String		name;
	@Getter
	@Setter
	private String		version;
	@Getter
	@Setter
	private String		filename;
	@Getter
	@Setter
	private World		world;
	@Getter
	@Setter
	private Location	observerspawn;
	@Getter
	@Setter
	private Location	redspawn;
	@Getter
	@Setter
	private Location	bluespawn;

	public SMap(FileConfiguration conf, World world, String filename) {
		setName(conf.getString("name")); 
		setFilename(filename);
		setWorld(world);
		setVersion(conf.getString("version"));
		/*setObserverspawn(LocationUtils.stringToLoc(conf.getString("obsspawn"), Bukkit.getWorld(world.getName())));
		setRedspawn(LocationUtils.stringToLoc(conf.getString("redspawn"), Bukkit.getWorld(world.getName())));
		setBluespawn(LocationUtils.stringToLoc(conf.getString("bluespawn"), Bukkit.getWorld(world.getName())));*/
	}

}
