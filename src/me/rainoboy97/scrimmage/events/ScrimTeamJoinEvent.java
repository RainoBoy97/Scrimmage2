package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimTeamJoinEvent extends Event {

	Player p;
	Team t;
	Team f;
	TeamHandler th;

	public ScrimTeamJoinEvent(Player p, Team t, Team f, TeamHandler th) {
		this.p = p;
		this.t = t;
		this.f = f;
		this.th = th;
	}

	public Player getPlayer() {
		return p;
	}
	
	public Team getNewTeam() {
		return t;
	}
	
	public Team getOldTeam() {
		return f;
	}
	
	public TeamHandler getTeamHandler() {
		return th;
	}
	
	public void setCancelled() {
		th.addPlayer(p, f);
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}