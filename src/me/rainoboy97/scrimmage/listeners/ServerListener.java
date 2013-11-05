package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {

	@EventHandler
	public void onPingEvent(ServerListPingEvent e) {
		e.setMotd(ScrimMatchHandler.getMotd());
	}

}
