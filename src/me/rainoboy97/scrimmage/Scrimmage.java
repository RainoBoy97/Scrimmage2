package me.rainoboy97.scrimmage;

import me.rainoboy97.scrimmage.commands.AdminCommands;
import me.rainoboy97.scrimmage.commands.UserCommands;
import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler;
import me.rainoboy97.scrimmage.handlers.ScrimTeamHandler.Team;
import me.rainoboy97.scrimmage.listeners.BlockListener;
import me.rainoboy97.scrimmage.listeners.ChatListener;
import me.rainoboy97.scrimmage.listeners.EntityListener;
import me.rainoboy97.scrimmage.listeners.PlayerListener;
import me.rainoboy97.scrimmage.listeners.ScrimEventListener;
import me.rainoboy97.scrimmage.listeners.ServerListener;
import me.rainoboy97.scrimmage.listeners.WorldListener;
import me.rainoboy97.scrimmage.utils.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Scrimmage extends JavaPlugin {

	private static Scrimmage i;

	public static boolean pvp = true;

	public Scrimmage() {
		Scrimmage.i = this;
	}

	public static Scrimmage get() {
		return i;
	}

	@Override
	public void onDisable() {
		ScrimMatchHandler.clean();
		ScrimMapHandler.clean();
		FileUtils.clean();
	}

	@Override
	public void onEnable() {
		FileUtils.clean();
		ScrimMatchHandler.initHandler();
		ScrimTeamHandler.loadTeams();
		if (ScrimMapHandler.loadMaps()) {
			ScrimMatchHandler.setMatch(ScrimMapHandler.getDefaultMap());
			ScrimMatchHandler.getCurrentMatch().loadMatch();
			ScrimMatchHandler.getCurrentMatch().teleportPlayers();
		} else {
			Bukkit.getServer().shutdown();
		}
		regListener(new BlockListener());
		regListener(new ChatListener());
		regListener(new EntityListener());
		regListener(new PlayerListener());
		regListener(new ScrimEventListener());
		regListener(new ServerListener());
		regListener(new WorldListener());

		regUserCommand("g");
		regUserCommand("operators");
		regUserCommand("join");
		regUserCommand("match");
		regUserCommand("maps");
		regAdminCommand("adminchat");
		regAdminCommand("pvp");
		regAdminCommand("setmatch");
		regAdminCommand("reloadmaps");
		for (Player p : Bukkit.getOnlinePlayers()) {
			ScrimTeamHandler.addPlayer(p, Team.OBSERVER);
		}
	}

	private void regListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	private void regAdminCommand(String cmd) {
		getCommand(cmd).setExecutor(new AdminCommands());
	}

	private void regUserCommand(String cmd) {
		getCommand(cmd).setExecutor(new UserCommands());
	}

	public static void msg(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "> "
				+ message);
	}

	public static String getPrefix(Player player) {
		return player.isOp() ? ChatColor.DARK_AQUA + "*" : "";
	}

	public static void logChat(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.stripColor(message));
	}

}
