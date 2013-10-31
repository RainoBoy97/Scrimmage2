package me.rainoboy97.scrimmage.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;

public class RegionUtils {

	public static Location getLocation(int x, int y, int z, World w) {
		return new Location(w, x, y, z);
	}

	public static Location[] getCube(Location l1, Location l2) {
		return null;
	}

	public static Location[] getCyl(Location c, int r, int h) {
		return null;
	}

	public static Location[] getRectangle(Location l1, Location l2) {
		return null;
	}

	public static Location[] getCircle(Location c, int r) {
		return null;
	}

	public static Location[] getUnion(ArrayList<Location> locs) {
		return null;
	}

}