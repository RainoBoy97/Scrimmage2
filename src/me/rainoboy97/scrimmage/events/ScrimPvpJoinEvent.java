package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimPvpJoinEvent extends Event {

	Player p;
	Team t;
	TeamHandler th;

	public ScrimPvpJoinEvent(Player p, Team t, TeamHandler th) {
		this.p = p;
		this.t = t;
		this.th = th;
	}

	public Player getPlayer() {
		return p;
	}
	
	public Team getTeam() {
		return t;
	}
	
	public TeamHandler getTeamHandler() {
		return th;
	}
	
	public void setCancelled() {
		th.addPlayer(p, Team.OBSERVER);
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}