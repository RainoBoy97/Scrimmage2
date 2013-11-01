package me.rainoboy97.scrimmage.utils;

import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.ScrimLogger;

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

	public static List<Location> getRegion(String input, World w) {
		String[] regions = input.split("|");
		int x = Integer.parseInt(regions[1]);
		int y = Integer.parseInt(regions[2]);
		int z = Integer.parseInt(regions[3]);
		List<Location> loc = new ArrayList<Location>();
		switch (regions[0].toLowerCase()) {
		case "cuboid":
			loc = RegionUtils.getCube(RegionUtils.getLocation(x, y, z, w),
					RegionUtils.getLocation(x, y, z, w));
			return loc;
		case "rectangle":
			loc = RegionUtils.getRectangle(RegionUtils.getLocation(x, y, z, w),
					RegionUtils.getLocation(x, y, z, w));
			return loc;
		default:
			ScrimLogger.severe("Invalid region type specified in map.yml");
			return loc;
		}
	}
}
