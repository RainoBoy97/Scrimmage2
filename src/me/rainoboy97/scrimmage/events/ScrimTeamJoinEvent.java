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

	public ScrimTeamJoinEvent(Player p, Team t, Team f) {
		this.p = p;
		this.t = t;
		this.f = f;
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

	public void setCancelled() {
		TeamHandler.addPlayer(p, f);
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