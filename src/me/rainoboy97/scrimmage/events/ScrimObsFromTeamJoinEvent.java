package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimObsFromTeamJoinEvent extends Event {

	Player p;
	Team pr;

	public ScrimObsFromTeamJoinEvent(Player p, Team pr) {
		this.p = p;
		this.pr = pr;
	}

	public Player getPlayer() {
		return p;
	}

	public Team getOldTeam() {
		return pr;
	}

	public void setCancelled() {
		ScrimTeamHandler.addPlayer(p, pr);
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