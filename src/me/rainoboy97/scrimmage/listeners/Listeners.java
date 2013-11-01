package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.events.ScrimObsFromTeamJoinEvent;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class Listeners implements Listener {

	@SuppressWarnings("unused")
	private final Scrimmage plugin;

	public Listeners(Scrimmage scrimmage) {
		plugin = scrimmage;
	}

	@EventHandler
	public void onObsJoin(ScrimObsFromTeamJoinEvent e) {
		e.getPlayer().setGameMode(GameMode.CREATIVE);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		TeamHandler.addPlayer(player, Team.OBSERVER);

		event.setJoinMessage(player.getDisplayName() + ChatColor.YELLOW
				+ " joined the game!");
		TeamHandler.loadScoreBoardPlayer(player);
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
	public void onPlayerKick(PlayerKickEvent event) {

	}

	@EventHandler
	public void event_interact(PlayerInteractEvent event) {
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
	public void event_interactentity(PlayerInteractEntityEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_itemdrop(PlayerDropItemEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.getItemDrop().remove();
		}
	}

	@EventHandler
	public void event_itemdrop(PlayerPickupItemEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_break(BlockBreakEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_place(BlockPlaceEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| TeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_fromto(BlockFromToEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_form(BlockFormEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_physics(BlockPhysicsEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_fade(BlockFadeEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_decay(LeavesDecayEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_burn(BlockBurnEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_grow(BlockGrowEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_damage(BlockDamageEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_ignite(BlockIgniteEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_spread(BlockSpreadEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_entityform(EntityBlockFormEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_grow(StructureGrowEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_explode(EntityExplodeEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_weatherchange(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_portal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void event_entityspawn(CreatureSpawnEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| event.getEntity() instanceof Monster) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event_shootbow(EntityShootBowEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
			return;
		}
		if (event.getEntity() instanceof Player) {
			if (TeamHandler.isObserver((Player) event.getEntity())) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void event_damage(EntityDamageByEntityEvent event) {
		if (!ScrimMatchHandler.isRunning() || !Scrimmage.pvp) {
			event.setCancelled(true);
			return;
		}
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (TeamHandler.isObserver(damager)) {
				event.setCancelled(true);
				return;
			}
		} else if (event.getDamager() instanceof Projectile) {
			Projectile proj = (Projectile) event.getDamager();
			if (proj.getShooter() instanceof Player) {
				Player shooter = (Player) proj.getShooter();
				if (TeamHandler.isObserver(shooter)) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

}
