package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
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

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
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
	public void onEntityBlockForm(EntityBlockFormEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| event.getEntity() instanceof Monster) {
			event.setCancelled(true);
		}
	}

}
