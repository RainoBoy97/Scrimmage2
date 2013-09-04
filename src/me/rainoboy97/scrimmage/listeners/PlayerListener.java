package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class PlayerListener implements Listener {

	@SuppressWarnings("unused")
	private Scrimmage plugin;
	private TeamHandler th;

	public PlayerListener(Scrimmage scrimmage) {
		this.plugin = scrimmage;
		this.th = Scrimmage.getTH();
	}

	@EventHandler
	public void event_join(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		th.addPlayer(player, Team.OBSERVER);
		
		event.setJoinMessage(Scrimmage.getPrefix(player) + th.getTeamColor(th.getTeam(player)) + player.getName() + ChatColor.YELLOW + " joined the game!");
	}

	@EventHandler
	public void event_quit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		event.setQuitMessage(Scrimmage.getPrefix(player) + th.getTeamColor(th.getTeam(player)) + player.getName() + ChatColor.YELLOW + " left the game!");
		
		th.removePlayer(player);
	}

	@EventHandler
	public void event_kick(PlayerKickEvent event) {

	}

	@EventHandler
	public void event_asynchat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		for (String p : th.getPlayersOnTeam(th.getTeam(player))) {
			Player t = Bukkit.getPlayerExact(p);
			t.sendMessage(ChatColor.GRAY + "[T] " + Scrimmage.getPrefix(player) + th.getTeamColor(th.getTeam(player)) + player.getName() + ChatColor.WHITE + ": " + event.getMessage());
		}
		event.setCancelled(true);
	}

	@EventHandler
	public void event_interact(PlayerInteractEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_interactentity(PlayerInteractEntityEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void event_itemdrop(PlayerDropItemEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.getItemDrop().remove();
		}
	}
	
	@EventHandler
	public void event_itemdrop(PlayerPickupItemEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_break(BlockBreakEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_place(BlockPlaceEvent event) {
		if (!MatchHandler.running() || th.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_fromto(BlockFromToEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_form(BlockFormEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_physics(BlockPhysicsEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_fade(BlockFadeEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_decay(LeavesDecayEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_burn(BlockBurnEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_grow(BlockGrowEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_damage(BlockDamageEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_ignite(BlockIgniteEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_spread(BlockSpreadEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_entityform(EntityBlockFormEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_grow(StructureGrowEvent event) {
		if (!MatchHandler.running()) {
			event.setCancelled(true);
		}
	}

}
