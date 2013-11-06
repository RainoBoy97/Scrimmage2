package me.rainoboy97.scrimmage.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class RegionUtils {

	public static Location getLocation(int x, int y, int z, World w) {
		return new Location(w, x, y, z);
	}

	public static List<Location> getCube(Location l1, Location l2) {
		World w = l1.getWorld();
		int x1 = l1.getBlockX();
		int x2 = l2.getBlockX();
		int y1 = l1.getBlockY();
		int y2 = l2.getBlockY();
		int z1 = l1.getBlockZ();
		int z2 = l2.getBlockZ();

		int MinX, MinY, MinZ, MaxX, MaxY, MaxZ;

		if (x1 < x2) {
			MinX = x1;
			MaxX = x2;
		} else {
			MinX = x2;
			MaxX = x1;
		}

		if (z1 < z2) {
			MinZ = z1;
			MaxZ = z2;
		} else {
			MinZ = z2;
			MaxZ = z1;
		}

		if (y1 < y2) {
			MinY = y1;
			MaxY = y2;
		} else {
			MinY = y2;
			MaxY = y1;
		}

		List<Location> loc = new ArrayList<Location>();

		for (int x = MinX; x <= MaxX; x++) {
			for (int y = MinY; y <= MaxY; y++) {
				for (int z = MinZ; z <= MaxZ; z++) {
					loc.add(getLocation(x, y, z, w));
				}
			}
		}

		return loc;

	}

	public static List<Location> getCyl(Location c, int r, int h) {
		List<Location> loc = new ArrayList<Location>();
		int cx = c.getBlockX();
		int cy = c.getBlockY();
		int cz = c.getBlockZ();
		World w = c.getWorld();
		int rSquared = r * r;
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
					for (int y = cy; y <= cy + h; y++) {
						loc.add(getLocation(x, y, z, w));
					}
				}
			}
		}
		return loc;
	}

	public static List<Location> getRectangle(Location l1, Location l2) {
		World w = l1.getWorld();
		return getCube(getLocation(l1.getBlockX(), 0, l1.getBlockZ(), w),
				getLocation(l2.getBlockX(), 256, l2.getBlockZ(), w));
	}

	public static List<Location> getCircle(Location c, int r) {
		int x = c.getBlockX();
		int y = 0;
		int z = c.getBlockZ();
		World w = c.getWorld();
		Location center = getLocation(x, y, z, w);
		return getCyl(center, r, 256);
	}

	public static List<Location> getUnion(List<List<Location>> locs) {
		List<Location> floc = new ArrayList<Location>();
		for (List<Location> listloc : locs) {
			for (Location location : listloc) {
				floc.add(location);
			}
		}
		return floc;
	}

}