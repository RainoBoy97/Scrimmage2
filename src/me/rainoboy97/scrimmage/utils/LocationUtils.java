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
		int x1 = Integer.parseInt(regions[1]);
		int y1 = Integer.parseInt(regions[2]);
		int z1 = Integer.parseInt(regions[3]);
		int x2 = Integer.parseInt(regions[4]);
		int y2 = Integer.parseInt(regions[5]);
		int z2 = Integer.parseInt(regions[6]);

		int cx = Integer.parseInt(regions[1]);
		int cy = Integer.parseInt(regions[2]);
		int cz = Integer.parseInt(regions[3]);
		int r = Integer.parseInt(regions[4]);
		int h = Integer.parseInt(regions[5]);

		List<Location> loc = new ArrayList<Location>();
		switch (regions[0].toLowerCase()) {
		case "cuboid":
			loc = RegionUtils.getCube(RegionUtils.getLocation(x1, y1, z1, w),
					RegionUtils.getLocation(x2, y2, z2, w));
			return loc;
		case "rectangle":
			loc = RegionUtils.getRectangle(
					RegionUtils.getLocation(x1, y1, z1, w),
					RegionUtils.getLocation(x2, y2, z2, w));
			return loc;
		case "cylinder":
			loc = RegionUtils.getCyl(RegionUtils.getLocation(cx, cy, cz, w), r,
					h);
			return loc;
		case "circle":
			loc = RegionUtils.getCircle(RegionUtils.getLocation(cx, cy, cz, w),
					r);
			return loc;
		default:
			ScrimLogger.severe("Invalid region type specified in map.yml");
			return loc;
		}
	}
}
