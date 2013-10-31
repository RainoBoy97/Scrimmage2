package me.rainoboy97.scrimmage.commands;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.events.ScrimObsFromTeamJoinEvent;
import me.rainoboy97.scrimmage.events.ScrimPvpJoinEvent;
import me.rainoboy97.scrimmage.events.ScrimTeamJoinEvent;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.match.MatchHandler;
import me.rainoboy97.scrimmage.utils.TimeUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommands implements CommandExecutor {

	private final TeamHandler th;

	public UserCommands() {
		th = Scrimmage.getTH();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;

		// G
		if (cmd.getName().equalsIgnoreCase("g")) {
			if (args.length == 0) {
				Scrimmage.msg(player, ChatColor.RED + "/g <message>");
				return true;
			}
			String msg = StringUtils.join(args, " ", 0, args.length);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(ChatColor.GRAY + "[G] " + player.getDisplayName()
						+ ChatColor.WHITE + ": " + msg);
			}
			Scrimmage.logChat("[G] " + Scrimmage.getPrefix(player)
					+ player.getName() + ": " + msg);

		}
		// JOIN
		if (cmd.getName().equalsIgnoreCase("join")) {
			Team oldteam;
			Team team;
			boolean obs = false;
			oldteam = th.getTeam((Player) sender);
			if (args.length == 1) {
				team = Team.getTeam(args[0]);
			} else {
				team = th.getTeamWithLessPlayers();
			}
			if (team == Team.OBSERVER) {
				obs = true;
			}
			if (MatchHandler.played() && !obs) {
				Scrimmage.msg(player, ChatColor.RED + "Match is over - "
						+ ChatColor.DARK_AQUA
						+ "You cannot join a team until the next match!");
				return true;
			}
			if (team == null) {
				Scrimmage.msg(player, ChatColor.RED + "Invalid team!");
				return true;
			}
			if (th.isJoined(player) && !obs) {
				Scrimmage.msg(player, ChatColor.RED
						+ "You have already joined a team!");
				return true;
			}
			th.addPlayer(player, team);
			Scrimmage
					.msg(player,
							ChatColor.GRAY
									+ "You joined team "
									+ th.getTeamColor(team)
									+ StringUtils.capitalize(team.name()
											.toLowerCase()));
			Bukkit.getPluginManager().callEvent(
					new ScrimTeamJoinEvent(player, team, oldteam, th));
			if (team == Team.OBSERVER) {
				Bukkit.getPluginManager().callEvent(
						new ScrimObsFromTeamJoinEvent(player, oldteam, th));
			} else {
				Bukkit.getPluginManager().callEvent(
						new ScrimPvpJoinEvent(player, team, th));
			}

		}

		// OPERATORS
		if (cmd.getName().equalsIgnoreCase("operators")) {
			StringBuilder sb = new StringBuilder(ChatColor.GRAY + "Operators: ");
			for (OfflinePlayer op : Bukkit.getOperators()) {
				if (op.isOnline()) {
					sb.append(ChatColor.GOLD + op.getName() + ChatColor.AQUA
							+ ", ");
					continue;
				}
				sb.append(ChatColor.GOLD + "" + ChatColor.ITALIC + op.getName()
						+ ChatColor.AQUA + ", ");
			}
			String ops = sb.toString().trim().substring(0, sb.length() - 2);
			player.sendMessage(ops);
		}

		// MATCH
		if (cmd.getName().equalsIgnoreCase("match")) {
			player.sendMessage(ChatColor.RED + "--------------- "
					+ ChatColor.GRAY + "Match info " + ChatColor.RED
					+ "---------------");
			if (!MatchHandler.running() && !MatchHandler.played()) {
				player.sendMessage(ChatColor.DARK_AQUA + "No match running!");
			} else {
				player.sendMessage(ChatColor.GRAY + "Time: " + ChatColor.GREEN
						+ TimeUtils.formatIntoHHMMSS(MatchHandler.getTime()));
			}
			player.sendMessage(ChatColor.RED + "Red: " + ChatColor.GRAY
					+ th.count(Team.RED) + ChatColor.DARK_AQUA + " | "
					+ ChatColor.BLUE + "Blue: " + ChatColor.GRAY
					+ th.count(Team.BLUE) + ChatColor.DARK_AQUA + " | "
					+ ChatColor.AQUA + "Observer: " + ChatColor.GRAY
					+ th.count(Team.OBSERVER));
		}

		return true;
	}
}
