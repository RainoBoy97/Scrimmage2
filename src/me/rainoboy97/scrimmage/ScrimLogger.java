package me.rainoboy97.scrimmage;

import java.util.logging.Level;

import org.bukkit.Bukkit;

public class ScrimLogger {

	public static void info(String msg) {
		Bukkit.getLogger().log(Level.INFO, "[Scrimmage] " + msg);
	}

	public static void warn(String msg) {
		Bukkit.getLogger().log(Level.WARNING, "[Scrimmage] " + msg);
	}

	public static void severe(String msg) {
		Bukkit.getLogger().log(Level.SEVERE, "[Scrimmage] " + msg);
	}

	public static void alert(String msg) {
		Bukkit.getLogger().log(Level.SEVERE,
				"#####[SCRIMMAGE ALERT]##### " + msg);
	}

}