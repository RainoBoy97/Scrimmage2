package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.utils.FileUtils;
import me.rainoboy97.scrimmage.utils.LocationUtils;
import me.rainoboy97.scrimmage.utils.RegionUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;

public class ScrimMatch {

	ScrimMap map;
	Integer matchid;

	World world;
	Location red_spawn;
	Location blue_spawn;
	Location obs_spawn;
	List<Location> playable;

	ScrimMatchState current;

	public ScrimMatch(Integer id, ScrimMap map) {
		current = ScrimMatchState.NONE;
		this.map = map;
		matchid = id;
	}

	private void setMatchState(ScrimMatchState sms) {
		current = sms;
	}

	public boolean isPlayable(Block b) {
		if (playable.contains(b.getLocation()))
			return true;
		else
			return false;
	}

	public void loadMatch() {
		if (current.equals(ScrimMatchState.NONE)) {
			File orig = map.getOrigDir();
			File dest = new File(Bukkit.getWorldContainer() + "\\match-"
					+ matchid);
			try {
				FileUtils.copyFolder(orig, dest);
			} catch (IOException e) {
				ScrimLogger
						.severe("Could not load match due to a copying error!");
				e.printStackTrace();
			}
			Bukkit.createWorld(new WorldCreator("match-" + matchid));
			world = Bukkit.getWorld("match-" + matchid);
			red_spawn = LocationUtils.stringToLoc(
					map.getYaml().getString("map.spawns.red"), world);
			blue_spawn = LocationUtils.stringToLoc(
					map.getYaml().getString("map.spawns.blue"), world);
			obs_spawn = LocationUtils.stringToLoc(
					map.getYaml().getString("map.spawns.obs"), world);
			List<String> regions = map.getYaml().getStringList("map.playable");
			List<List<Location>> locations = new ArrayList<List<Location>>();
			for (String region : regions) {
				locations.add(LocationUtils.getRegion(region, world));
			}
			playable = RegionUtils.getUnion(locations);
			setMatchState(ScrimMatchState.LOADED);
		} else {
			ScrimLogger.severe("Match could not be loaded: Invalid State");
		}
	}

	public void teleportPlayers() {

	}

	public ScrimMatchState getMatchState() {
		return current;
	}

	public enum ScrimMatchState {
		NONE, LOADED, TELEPORTED, STARTING, RUNNING, ENDED;
	}

}