package me.rainoboy97.scrimmage.events;

import java.io.File;

import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.utils.FileUtils;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimMapCopyEvent extends Event {

	File s;
	File d;
	String fn;
	ScrimMap m;
	boolean sc;

	public ScrimMapCopyEvent(ScrimMap map, File source, File destination,
			String filename, boolean success) {
		s = source;
		d = destination;
		fn = filename;
		m = map;
		sc = success;
	}

	public ScrimMap getMap() {
		return m;
	}

	public File getSource() {
		return s;
	}

	public File getDestination() {
		return d;
	}

	public String getFilename() {
		return fn;
	}

	public boolean getOutcome() {
		return sc;
	}
	
	public void setCancelled() {
		if (d.exists()) {
			FileUtils.deleteDirectory(d);
		}
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}