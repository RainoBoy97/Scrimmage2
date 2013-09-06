package me.rainoboy97.scrimmage.map;

import java.io.File;
import java.util.HashMap;

import me.rainoboy97.scrimmage.Scrimmage;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MapHandler {

	private static HashMap<World, MapConfig> maps = new HashMap<World, MapConfig>();

	public static void setNext(Player player, String mapname) {
		File world = new File(Bukkit.getWorldContainer() + File.separator + mapname);
		if (!world.exists()) {
			Scrimmage.msg(player, ChatColor.RED + "Map not found!");
			return;
		}
		if (!new File(world, "map.yml").exists()) {
			Scrimmage.msg(player, ChatColor.RED + "Map does not contain map.yml!");
			return;
		}
		Scrimmage.msg(player, ChatColor.GREEN + "Next map set to " + ChatColor.GRAY + StringUtils.capitalize(mapname));
	}
	
	public static void addMap(World world, MapConfig config) {
		maps.put(world, config);
	}

}
