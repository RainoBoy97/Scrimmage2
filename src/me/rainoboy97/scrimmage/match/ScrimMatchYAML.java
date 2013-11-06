package me.rainoboy97.scrimmage.match;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler.Team;
import me.rainoboy97.scrimmage.utils.LocationUtils;
import me.rainoboy97.scrimmage.utils.RegionUtils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScrimMatchYAML {

	FileConfiguration f;
	World w;

	List<Location> red_spawn;
	List<Location> blue_spawn;
	List<Location> obs_spawn;
	@Nonnull
	long timelock;
	List<Location> playable;
	List<Location> unplayable;
	List<Location> unplayable_red;
	List<Location> unplayable_blue;
	BukkitRunnable br;

	public ScrimMatchYAML(FileConfiguration yml, World wo) {
		f = yml;
		w = wo;
	}

	public void loadYAML() {
		List<String> regions_red = f.getStringList("map.spawns.red");
		List<List<Location>> locations_red = new ArrayList<List<Location>>();
		for (String region : regions_red) {
			locations_red.add(LocationUtils.getRegion(region, w));
		}
		red_spawn = RegionUtils.getUnion(locations_red);
		List<String> regions_blue = f.getStringList("map.spawns.blue");
		List<List<Location>> locations_blue = new ArrayList<List<Location>>();
		for (String region : regions_blue) {
			locations_blue.add(LocationUtils.getRegion(region, w));
		}
		blue_spawn = RegionUtils.getUnion(locations_blue);
		List<String> regions_obs = f.getStringList("map.spawns.obs");
		List<List<Location>> locations_obs = new ArrayList<List<Location>>();
		for (String region : regions_obs) {
			locations_obs.add(LocationUtils.getRegion(region, w));
		}
		obs_spawn = RegionUtils.getUnion(locations_obs);
		timelock = f.getLong("map.timelock");

		br = new BukkitRunnable() {

			@Override
			public void run() {
				w.setTime(timelock);
			}

		};
		if (timelock != 0) {
			br.runTaskTimer(Scrimmage.get(), 0L, 200L);
		}
		List<String> regions = f.getStringList("map.regions.playable");
		List<List<Location>> locations = new ArrayList<List<Location>>();
		for (String region : regions) {
			locations.add(LocationUtils.getRegion(region, w));
		}
		playable = RegionUtils.getUnion(locations);
		List<String> regions_un = f.getStringList("map.regions.unplayable");
		List<List<Location>> locations_un = new ArrayList<List<Location>>();
		for (String region : regions_un) {
			locations_un.add(LocationUtils.getRegion(region, w));
		}
		unplayable = RegionUtils.getUnion(locations_un);
		playable = RegionUtils.getExclude(playable, unplayable);
		List<String> regions_unr = f
				.getStringList("map.regions.unplayable_red");
		List<List<Location>> locations_unr = new ArrayList<List<Location>>();
		for (String region : regions_unr) {
			locations_unr.add(LocationUtils.getRegion(region, w));
		}
		unplayable_red = RegionUtils.getUnion(locations_unr);
		List<String> regions_unb = f
				.getStringList("map.regions.unplayable_blue");
		List<List<Location>> locations_unb = new ArrayList<List<Location>>();
		for (String region : regions_unb) {
			locations_unb.add(LocationUtils.getRegion(region, w));
		}
		unplayable_blue = RegionUtils.getUnion(locations_unb);
	}

	public boolean isPlayableTeam(Team t, Location l) {
		switch (t) {
		case RED:
			if (unplayable_red.contains(l.getBlock().getLocation()))
				return false;
			else
				return true;
		case BLUE:
			if (unplayable_blue.contains(l.getBlock().getLocation()))
				return false;
			else
				return true;
		default:
			return true;

		}
	}

	public boolean isPlayable(Block b, Player p) {
		if (playable.contains(b.getLocation())
				&& isPlayableTeam(ScrimTeamHandler.getTeam(p), b.getLocation()))
			return true;
		else
			return false;
	}

	public boolean isPlayable(Location l, Player p) {
		if (playable.contains(l.getBlock().getLocation())
				&& isPlayableTeam(ScrimTeamHandler.getTeam(p), l.getBlock()
						.getLocation()))
			return true;
		else
			return false;
	}

	public Location getObsSpawn() {
		int biggest = obs_spawn.size() - 1;
		int random = (int) (Math.random() * (biggest + 1));
		return obs_spawn.get(random);
	}

	public Location getBlueSpawn() {
		int biggest = blue_spawn.size() - 1;
		int random = (int) (Math.random() * (biggest + 1));
		return blue_spawn.get(random);
	}

	public Location getRedSpawn() {
		int biggest = red_spawn.size() - 1;
		int random = (int) (Math.random() * (biggest + 1));
		return red_spawn.get(random);
	}

	public void cancelTimelock() {
		if (timelock != 0) {
			br.cancel();
		}
	}

}
