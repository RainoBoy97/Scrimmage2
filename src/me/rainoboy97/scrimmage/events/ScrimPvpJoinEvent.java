package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimPvpJoinEvent extends Event {

	Player p;
	Team t;

	public ScrimPvpJoinEvent(Player p, Team t) {
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