package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.io.IOException;

import me.rainoboy97.scrimmage.ScrimLogger;
import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.events.ScrimLoadMatchEvent;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler.Team;
import me.rainoboy97.scrimmage.utils.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ScrimMatch {

	ScrimMap map;
	Integer matchid;
	Scrimmage pl;

	ScrimMatchYAML yml;

	World world;

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
		return yml.isPlayableTeam(t, l);
	}

	public boolean isPlayable(Block b, Player p) {
		return yml.isPlayable(b, p);
	}

	public boolean isPlayable(Location l, Player p) {
		return yml.isPlayable(l, p);
	}

	public Location getObsSpawn() {
		return yml.getObsSpawn();
	}

	public Location getBlueSpawn() {
		return yml.getObsSpawn();
	}

	public Location getRedSpawn() {
		return yml.getRedSpawn();
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
			yml = new ScrimMatchYAML(map.getYaml(), world);
			yml.loadYAML();
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
		yml.cancelTimelock();
	}

	public void end() {
		current = ScrimMatchState.ENDED;
		yml.cancelTimelock();
	}

	public ScrimMatchState getMatchState() {
		return current;
	}

	public enum ScrimMatchState {
		NONE, LOADED, TELEPORTED, STARTING, RUNNING, ENDED;
	}

}