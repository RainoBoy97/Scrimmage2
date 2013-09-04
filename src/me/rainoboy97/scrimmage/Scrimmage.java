package me.rainoboy97.scrimmage;

import me.rainoboy97.scrimmage.commands.AdminCommands;
import me.rainoboy97.scrimmage.commands.UserCommands;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Scrimmage extends JavaPlugin {

	private static Scrimmage i;
	private static TeamHandler th;
	private static MatchHandler mh;

	public Scrimmage() {
		Scrimmage.i = this;
		Scrimmage.th = new TeamHandler();
		Scrimmage.mh = new MatchHandler();
	}

	public static Scrimmage get() {
		return i;
	}

	public void onDisable() {
		if(MatchHandler.running()) {
			MatchHandler.stop(null);
		}
	}

	public void onEnable() {
		this.regListener(new PlayerListener(this));

		this.regUserCommand("g");
		this.regUserCommand("join");
		this.regAdminCommand("start");
		this.regAdminCommand("cancel");
		this.regAdminCommand("end");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			th.addPlayer(p, Team.OBSERVER);
		}
	}

	private void regListener(Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}

	private void regAdminCommand(String cmd) {
		this.getCommand(cmd).setExecutor(new AdminCommands());
	}

	private void regUserCommand(String cmd) {
		this.getCommand(cmd).setExecutor(new UserCommands());
	}

	public static void msg(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "> " + message);
	}

	public static String getPrefix(Player player) {
		return player.isOp() ? ChatColor.DARK_AQUA + "*" : "";
	}

	public static TeamHandler getTH() {
		return Scrimmage.th;
	}
	
	public static MatchHandler getMH() {
		return Scrimmage.mh;
	}
	
}
