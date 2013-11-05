package me.rainoboy97.scrimmage.events;

import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.match.ScrimMatch;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ScrimSetMatchEvent extends Event {

	String mname;
	ScrimMatch thismatch;
	ScrimMatch prevmatch;

	public ScrimSetMatchEvent(String mn, ScrimMatch tmatch, ScrimMatch pmatch) {
		mname = mn;
		thismatch = tmatch;
		prevmatch = pmatch;
	}

	public String getMapName() {
		return mname;
	}

	public ScrimMap getMap() {
		return ScrimMapHandler.get(mname);
	}

	public ScrimMatch getNewMatch() {
		return thismatch;
	}

	public ScrimMatch getPrevMatch() {
		return prevmatch;
	}

	public void setCancelled() {
		ScrimMatchHandler.setMatch(prevmatch);
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}