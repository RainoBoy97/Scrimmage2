package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.events.ScrimLoadMatchEvent;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.utils.FileUtils;
import me.rainoboy97.scrimmage.utils.LocationUtils;
import me.rainoboy97.scrimmage.utils.RegionUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScrimMatch {

	ScrimMap map;
	Integer matchid;
	Scrimmage pl;

	World world;
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

	ScrimMatchState current;

	public ScrimMatch(Integer id, ScrimMap map) {
		current = ScrimMatchState.NONE;
		this.map = map;
		matchid = id;
		pl = Scrimmage.get();
	}

	public int getMatchId() {
		return matchid;
	}

	public String getMapName() {
		return map.getDirName();
	}

	public ScrimMap getMap() {
		return map;
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
				&& isPlayableTeam(TeamHandler.getTeam(p), b.getLocation()))
			return true;
		else
			return false;
	}

	public boolean isPlayable(Location l, Player p) {
		if (playable.contains(l.getBlock().getLocation())
				&& isPlayableTeam(TeamHandler.getTeam(p), l.getBlock()
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

	public void loadMatch() {
		boolean outcome = false;
		if (current.equals(ScrimMatchState.NONE)) {
			File orig = map.getOrigDir();
			File dest = new File(Bukkit.getWorldContainer() + "\\match-"
					+ matchid);
			try {
				FileUtils.copyFolder(orig, dest);
				outcome = true;
			} catch (IOException e) {
				outcome = false;
				ScrimLogger
						.severe("Could not load match due to a copying error!");
				e.printStackTrace();
			}
			world = Bukkit.getServer().createWorld(
					new WorldCreator("match-" + matchid));
			List<String> regions_red = map.getYaml().getStringList(
					"map.spawns.red");
			List<List<Location>> locations_red = new ArrayList<List<Location>>();
			for (String region : regions_red) {
				locations_red.add(LocationUtils.getRegion(region, world));
			}
			red_spawn = RegionUtils.getUnion(locations_red);
			List<String> regions_blue = map.getYaml().getStringList(
					"map.spawns.blue");
			List<List<Location>> locations_blue = new ArrayList<List<Location>>();
			for (String region : regions_blue) {
				locations_blue.add(LocationUtils.getRegion(region, world));
			}
			blue_spawn = RegionUtils.getUnion(locations_blue);
			List<String> regions_obs = map.getYaml().getStringList(
					"map.spawns.obs");
			List<List<Location>> locations_obs = new ArrayList<List<Location>>();
			for (String region : regions_obs) {
				locations_obs.add(LocationUtils.getRegion(region, world));
			}
			obs_spawn = RegionUtils.getUnion(locations_obs);
			timelock = map.getYaml().getLong("map.timelock");

			br = new BukkitRunnable() {

				@Override
				public void run() {
					world.setTime(timelock);
				}

			};
			if (timelock != 0) {
				br.runTaskTimer(Scrimmage.get(), 0L, 200L);
			}
			List<String> regions = map.getYaml().getStringList(
					"map.regions.playable");
			List<List<Location>> locations = new ArrayList<List<Location>>();
			for (String region : regions) {
				locations.add(LocationUtils.getRegion(region, world));
			}
			playable = RegionUtils.getUnion(locations);
			List<String> regions_un = map.getYaml().getStringList(
					"map.regions.unplayable");
			List<List<Location>> locations_un = new ArrayList<List<Location>>();
			for (String region : regions_un) {
				locations_un.add(LocationUtils.getRegion(region, world));
			}
			unplayable = RegionUtils.getUnion(locations_un);
			playable = RegionUtils.getExclude(playable, unplayable);
			List<String> regions_unr = map.getYaml().getStringList(
					"map.regions.unplayable_red");
			List<List<Location>> locations_unr = new ArrayList<List<Location>>();
			for (String region : regions_unr) {
				locations_unr.add(LocationUtils.getRegion(region, world));
			}
			unplayable_red = RegionUtils.getUnion(locations_unr);
			List<String> regions_unb = map.getYaml().getStringList(
					"map.regions.unplayable_blue");
			List<List<Location>> locations_unb = new ArrayList<List<Location>>();
			for (String region : regions_unb) {
				locations_unb.add(LocationUtils.getRegion(region, world));
			}
			unplayable_blue = RegionUtils.getUnion(locations_unb);
			current = ScrimMatchState.LOADED;
		} else {
			outcome = false;
			ScrimLogger.severe("Match could not be loaded: Invalid State");
		}
		Bukkit.getPluginManager().callEvent(
				new ScrimLoadMatchEvent(map, matchid, world, this, outcome));
	}

	public void teleportPlayers() {
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		for (Player p : players) {
			p.teleport(getObsSpawn());
		}
		current = ScrimMatchState.TELEPORTED;
	}

	public void start(Integer count) {
		current = ScrimMatchState.STARTING;
	}

	public void end(Team winner) {
		current = ScrimMatchState.ENDED;
		if (timelock != 0) {
			br.cancel();
		}

	}

	public void end() {
		current = ScrimMatchState.ENDED;
		if (timelock != 0) {
			br.cancel();
		}
	}

	public ScrimMatchState getMatchState() {
		return current;
	}

	public enum ScrimMatchState {
		NONE, LOADED, TELEPORTED, STARTING, RUNNING, ENDED;
	}

}