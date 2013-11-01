package me.rainoboy97.scrimmage;

import me.rainoboy97.scrimmage.commands.AdminCommands;
import me.rainoboy97.scrimmage.commands.UserCommands;
import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.listeners.BlockListeners;
import me.rainoboy97.scrimmage.listeners.ChatListeners;
import me.rainoboy97.scrimmage.listeners.CombatListeners;
import me.rainoboy97.scrimmage.listeners.EventCallListeners;
import me.rainoboy97.scrimmage.listeners.Listeners;
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
		if (ScrimMatchHandler.isRunning()) {
			ScrimMatchHandler.getCurrentMatch();
		}
	}

	@Override
	public void onEnable() {
		FileUtils.clean();
		ScrimMapHandler.loadMaps();
		ScrimMatchHandler.initHandler();
		regListener(new Listeners(this));
		regListener(new ChatListeners());
		regListener(new CombatListeners());
		regListener(new BlockListeners());
		regListener(new EventCallListeners());

		regUserCommand("g");
		regUserCommand("operators");
		regAdminCommand("a");
		regAdminCommand("pvp");

		TeamHandler.loadTeams();

		for (Player p : Bukkit.getOnlinePlayers()) {
			TeamHandler.addPlayer(p, Team.OBSERVER);
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
