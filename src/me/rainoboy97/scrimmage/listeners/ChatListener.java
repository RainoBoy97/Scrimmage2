package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (String p : ScrimTeamHandler.getPlayersOnTeam(ScrimTeamHandler
				.getTeam(player))) {
			Player t = Bukkit.getPlayerExact(p);
			t.sendMessage(ChatColor.GRAY + "[T] " + player.getDisplayName()
					+ ChatColor.WHITE + ": " + event.getMessage());
		}
		Scrimmage.logChat("[T] " + Scrimmage.getPrefix(player)
				+ player.getName() + ": " + event.getMessage());
		event.setCancelled(true);
	}

}
