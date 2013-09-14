package me.rainoboy97.scrimmage.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
	
	public static String locToString(Location loc) {
		String location = (loc.getX() + "|" + loc.getY() + "|" + loc.getZ() + "|" + loc.getYaw() + "|" + loc.getPitch());
		return location;
	}

	public static Location stringToLoc(String s) {
		String[] loc = s.split("|");
		World world = Bukkit.getWorld(loc[0]);
		double x = Double.parseDouble(loc[1]);
		double y = Double.parseDouble(loc[2]);
		double z = Double.parseDouble(loc[3]);
		double yaw = Double.parseDouble(loc[4]);
		double pitch = Double.parseDouble(loc[5]);
		Location finalloc = new Location(world, x, y, z, (float) yaw, (float) pitch);
		return finalloc;
	}
}
