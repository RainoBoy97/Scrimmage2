package me.rainoboy97.scrimmage.commands;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommands implements CommandExecutor {

	private TeamHandler th;

	public UserCommands() {
		this.th = Scrimmage.getTH();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;

		// G
		if (cmd.getName().equalsIgnoreCase("g")) {
			if (args.length < 0) {
				Scrimmage.msg(player, ChatColor.RED + "/g <message>");
				return true;
			}
			String msg = StringUtils.join(args, " ", 0, args.length);
			Bukkit.broadcastMessage(ChatColor.GRAY + "[G] " + Scrimmage.getPrefix(player) + th.getTeamColor(th.getTeam(player)) + player.getDisplayName() + ChatColor.WHITE + ": " + msg);
		}
		// JOIN
		if (cmd.getName().equalsIgnoreCase("join")) {
			if (args.length != 1) {
				Scrimmage.msg(player, ChatColor.RED + "/join <red|blue|observer>");
				return true;
			}
			Team team = Team.getTeam(args[0]);
			if (team == null) {
				Scrimmage.msg(player, ChatColor.RED + "Invalid team!");
				return true;
			}
			Scrimmage.getTH().addPlayer(player, team);
			Scrimmage.msg(player, ChatColor.GRAY + "You joined team " + team.color() + StringUtils.capitalize(team.name().toLowerCase()));
		}
		return true;
	}

}
