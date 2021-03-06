package me.rainoboy97.scrimmage.utils;

import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.ScrimLogger;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

	public static List<Location> getRegion(String input, World w) {
		String[] regions = input.split(":");
		List<Location> loc = new ArrayList<Location>();
		switch (regions[0]) {
		case "point":
			int x = Integer.parseInt(regions[1]);
			int y = Integer.parseInt(regions[2]);
			int z = Integer.parseInt(regions[3]);
			float yaw = Float.parseFloat(regions[4]);
			loc.add(new Location(w, x, y, z, yaw, 0));
			return loc;
		case "cuboid":
			int x1 = Integer.parseInt(regions[1]);
			int y1 = Integer.parseInt(regions[2]);
			int z1 = Integer.parseInt(regions[3]);
			int x2 = Integer.parseInt(regions[4]);
			int y2 = Integer.parseInt(regions[5]);
			int z2 = Integer.parseInt(regions[6]);
			loc = RegionUtils.getCube(RegionUtils.getLocation(x1, y1, z1, w),
					RegionUtils.getLocation(x2, y2, z2, w));
			return loc;
		case "rectangle":
			int x3 = Integer.parseInt(regions[1]);
			int z3 = Integer.parseInt(regions[2]);
			int x4 = Integer.parseInt(regions[3]);
			int z4 = Integer.parseInt(regions[4]);
			loc = RegionUtils.getRectangle(
					RegionUtils.getLocation(x3, 0, z3, w),
					RegionUtils.getLocation(x4, 256, z4, w));
			return loc;
		case "cylinder":
			int cx = Integer.parseInt(regions[1]);
			int cy = Integer.parseInt(regions[2]);
			int cz = Integer.parseInt(regions[3]);
			int r = Integer.parseInt(regions[4]);
			int h = Integer.parseInt(regions[5]);
			loc = RegionUtils.getCyl(RegionUtils.getLocation(cx, cy, cz, w), r,
					h);
			return loc;
		case "circle":
			int cx1 = Integer.parseInt(regions[1]);
			int cz1 = Integer.parseInt(regions[2]);
			int r1 = Integer.parseInt(regions[3]);
			loc = RegionUtils.getCircle(
					RegionUtils.getLocation(cx1, 0, cz1, w), r1);
			return loc;
		default:
			ScrimLogger.severe("Invalid region type specified in map.yml");
			return loc;
		}
	}
}
