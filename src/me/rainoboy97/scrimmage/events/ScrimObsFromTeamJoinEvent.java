package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimObsFromTeamJoinEvent extends Event {

	Player p;
	Team pr;
	TeamHandler th;

	public ScrimObsFromTeamJoinEvent(Player p, Team pr, TeamHandler th) {
		this.p = p;
		this.pr = pr;
		this.th = th;
	}

	public Player getPlayer() {
		return p;
	}
	
	public Team getOldTeam() {
		return pr;
	}
	
	public TeamHandler getTeamHandler() {
		return th;
	}
	
	public void setCancelled() {
		th.addPlayer(p, pr);
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}