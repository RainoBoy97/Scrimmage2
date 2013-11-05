package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class WorldListener implements Listener {

	@EventHandler
	public void onStructureGrow(StructureGrowEvent event) {
		if (!ScrimMatchHandler.isRunning()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			event.setCancelled(true);
		}
	}

}
