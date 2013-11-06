package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler;

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
import org.bukkit.event.block.LeavesDecayEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| ScrimTeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!ScrimMatchHandler.isRunning()
				|| ScrimTeamHandler.isObserver(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockForm(BlockFormEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockGrow(BlockGrowEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

}
