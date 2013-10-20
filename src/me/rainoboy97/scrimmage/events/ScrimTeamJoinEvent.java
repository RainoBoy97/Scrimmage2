package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimTeamJoinEvent extends Event {

	private Player p;
	private Team t;

	public ScrimTeamJoinEvent(Player p, Team t) {
		this.p = p;
		this.t = t;
	}

	public Player getPlayer() {
		return p;
	}
	
	public Team getTeam() {
		return t;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}