package me.rainoboy97.scrimmage;

import me.rainoboy97.scrimmage.commands.AdminCommands;
import me.rainoboy97.scrimmage.commands.UserCommands;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.listeners.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import fr.aumgn.bukkitutils.command.CommandsRegistration;

import java.util.Locale;

public class Scrimmage extends JavaPlugin {

	public static boolean pvp;
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
		if (MatchHandler.running()) {
			MatchHandler.stop(null);
		}
	}

	public void onEnable() {
		this.regListener(new Listeners(this));

		this.regUserCommand("g");
        this.regAdminCommand("restart");
        this.regUserCommand("join");
		this.regAdminCommand("a");
		this.regAdminCommand("start");
		this.regAdminCommand("cancel");
		this.regAdminCommand("end");
		this.regAdminCommand("setnext");
        this.regAdminCommand("pvp");

        CommandsRegistration registration = new CommandsRegistration(
        this, Locale.getDefault());
        registration.register(new AdminCommands());

		for (Player p : Bukkit.getOnlinePlayers()) {
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
