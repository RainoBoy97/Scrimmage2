package me.rainoboy97.scrimmage.map;

import lombok.Getter;
import lombok.Setter;

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
	}

}
