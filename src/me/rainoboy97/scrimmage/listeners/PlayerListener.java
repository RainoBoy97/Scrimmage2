package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		TeamHandler.addPlayer(player, Team.OBSERVER);

		event.setJoinMessage(player.getDisplayName() + ChatColor.YELLOW
				+ " joined the game!");
		TeamHandler.loadScoreBoardPlayer(player);
		player.teleport(ScrimMatchHandler.getCurrentTeleport());
	}

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Team t = TeamHandler.getTeam(p);
		switch (t) {
		case RED:
			Location sl1 = ScrimMatchHandler.getCurrentMatch().getRedSpawn();
			e.setRespawnLocation(sl1);
			break;
		case BLUE:
			Location sl2 = ScrimMatchHandler.getCurrentMatch().getBlueSpawn();
			e.setRespawnLocation(sl2);
			break;
		default:
			Location sl3 = ScrimMatchHandler.getCurrentTeleport();
			e.setRespawnLocation(sl3);
			break;
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		String o_msg = e.getDeathMessage();
		Player player = e.getEntity();
		String name = player.getDisplayName();
		String f_name = name;
		if (name.contains("*")) {
			f_name = name.replace(ChatColor.DARK_AQUA + "*", "");
		}
		String f_msg = o_msg.replace(player.getName() + " ", "");
		if (player.getKiller() instanceof Player) {
			Player kill = player.getKiller();
			String killer = kill.getDisplayName();
			String f_killer = killer;
			if (killer.contains("*")) {
				f_killer = killer.replace(ChatColor.DARK_AQUA + "*", "");
			}
			String ff_msg = f_msg.replace(kill.getName(), f_killer);
			e.setDeathMessage(f_name + ChatColor.GRAY + " " + ff_msg);
		} else {
			e.setDeathMessage(f_name + ChatColor.GRAY + " " + f_msg);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
		if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		if (!(event.getClickedBlock().getType() == Material.WORKBENCH))
			return;
		event.setCancelled(true);
		event.getPlayer().openWorkbench(null, true);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		event.setQuitMessage(player.getDisplayName() + ChatColor.YELLOW
				+ " left the game!");
		TeamHandler.removePlayer(player);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.getItemDrop().remove();
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

}
