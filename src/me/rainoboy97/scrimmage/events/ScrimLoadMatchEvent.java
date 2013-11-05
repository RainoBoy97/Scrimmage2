package me.rainoboy97.scrimmage.events;

import java.io.File;

import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.match.ScrimMatch;
import me.rainoboy97.scrimmage.utils.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimLoadMatchEvent extends Event {

	ScrimMap map;
	Integer matchid;
	World world;
	ScrimMatch match;
	boolean outcome;

	public ScrimLoadMatchEvent(ScrimMap mp, Integer id, World w, ScrimMatch m,
			boolean outcome) {
		map = mp;
		matchid = id;
		world = w;
		match = m;
		this.outcome = outcome;
	}

	public ScrimMap getMap() {
		return map;
	}

	public int getMatchId() {
		return matchid;
	}

	public World getWorld() {
		return world;
	}

	public ScrimMatch getMatch() {
		return match;
	}

	public boolean getOutcome() {
		return outcome;
	}

	public void setCancelled() {
		if (outcome) {
			FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer()
					+ "\\match-" + matchid));
		}
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}