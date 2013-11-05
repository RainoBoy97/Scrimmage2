package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.events.ScrimObsFromTeamJoinEvent;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ScrimEventListener implements Listener {

	@EventHandler
	public void onObsJoin(ScrimObsFromTeamJoinEvent e) {
		e.getPlayer().setGameMode(GameMode.CREATIVE);
	}

}