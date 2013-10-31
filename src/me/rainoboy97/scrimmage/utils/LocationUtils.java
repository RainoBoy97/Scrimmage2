package me.rainoboy97.scrimmage.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

	public static String locToString(Location loc) {
		String location = loc.getX() + "|" + loc.getY() + "|" + loc.getZ()
				+ "|" + loc.getYaw() + "|" + loc.getPitch();
		return location;
	}

	public static Location stringToLoc(String stringloc, World world) {
		String[] loc = stringloc.split("|");
		double x = Double.parseDouble(loc[0]);
		double y = Double.parseDouble(loc[1]);
		double z = Double.parseDouble(loc[2]);
		double yaw = Double.parseDouble(loc[3]);
		double pitch = Double.parseDouble(loc[4]);
		Location finalloc = new Location(world, x, y, z, (float) yaw,
				(float) pitch);
		return finalloc;
	}
}
