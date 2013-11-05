package me.rainoboy97.scrimmage.commands;

import java.util.List;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.events.ScrimObsFromTeamJoinEvent;
import me.rainoboy97.scrimmage.events.ScrimPvpJoinEvent;
import me.rainoboy97.scrimmage.events.ScrimTeamJoinEvent;
import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.match.ScrimMatch.ScrimMatchState;
import me.rainoboy97.scrimmage.utils.LookupUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommands implements CommandExecutor {

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
			Team team = null;
			boolean obs = false;
			oldteam = TeamHandler.getTeam((Player) sender);
			if (args.length == 1) {
				int count = LookupUtils.countTeam(args[0]);
				if (count == 1) {
					team = LookupUtils.getTeam(args[0]);
				} else if (count > 1) {
					sender.sendMessage(ChatColor.AQUA + "" + count
							+ ChatColor.GREEN + " teams matched query "
							+ ChatColor.AQUA + args[0]);
				} else {
					sender.sendMessage(ChatColor.RED
							+ "No teams matched query " + ChatColor.AQUA
							+ args[0]);
				}
			} else {
				team = TeamHandler.getTeamWithLessPlayers();
			}
			if (team != null) {
				if (team == Team.OBSERVER) {
					obs = true;
				}
				if (ScrimMatchHandler.getCurrentMatch().getMatchState() == ScrimMatchState.ENDED
						&& !obs) {
					Scrimmage.msg(player, ChatColor.RED + "Match is over - "
							+ ChatColor.DARK_AQUA
							+ "You cannot join a team until the next match!");
					return true;
				}
				if (TeamHandler.isJoined(player) && !obs) {
					Scrimmage.msg(player, ChatColor.RED
							+ "You have already joined a team!");
					return true;
				}
				TeamHandler.addPlayer(player, team);
				Scrimmage.msg(
						player,
						ChatColor.GRAY
								+ "You joined team "
								+ TeamHandler.getTeamColor(team)
								+ StringUtils.capitalize(team.name()
										.toLowerCase()));
				Bukkit.getPluginManager().callEvent(
						new ScrimTeamJoinEvent(player, team, oldteam));
				if (team == Team.OBSERVER) {
					Bukkit.getPluginManager().callEvent(
							new ScrimObsFromTeamJoinEvent(player, oldteam));
				} else {
					Bukkit.getPluginManager().callEvent(
							new ScrimPvpJoinEvent(player, team));
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Could not join a team!");
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
			player.sendMessage(ChatColor.RED + "---------------"
					+ ChatColor.GRAY + " Match info " + ChatColor.RED
					+ "---------------");
			if (!ScrimMatchHandler.isRunning()
					&& !(ScrimMatchHandler.getCurrentMatch().getMatchState() == ScrimMatchState.ENDED)) {
				player.sendMessage(ChatColor.DARK_AQUA + "No match running!");
			}/*
			 * else { player.sendMessage(ChatColor.GRAY + "Time: " +
			 * ChatColor.GREEN +
			 * TimeUtils.formatIntoHHMMSS(MatchHandler.getTime())); }
			 */
			player.sendMessage(ChatColor.RED + "Red: " + ChatColor.GRAY
					+ TeamHandler.count(Team.RED) + ChatColor.DARK_AQUA + " | "
					+ ChatColor.BLUE + "Blue: " + ChatColor.GRAY
					+ TeamHandler.count(Team.BLUE) + ChatColor.DARK_AQUA
					+ " | " + ChatColor.AQUA + "Observer: " + ChatColor.GRAY
					+ TeamHandler.count(Team.OBSERVER));
		}

		// MAPS

		if (cmd.getName().equalsIgnoreCase("maps")) {
			if (args.length < 2) {
				List<ScrimMap> sm = ScrimMapHandler.getMapList();
				double totalmaps = sm.size();
				int pagenum = (int) Math.ceil(totalmaps / 10);
				int thispage = 1;
				if (args.length == 1) {
					thispage = Integer.parseInt(args[0]);
				}
				int startindex = thispage * 10 - 10;
				int endindex = startindex + 9;
				if (endindex > sm.size()) {
					endindex = sm.size();
				}
				player.sendMessage(ChatColor.RED + "---------------"
						+ ChatColor.GRAY + " Loaded maps (" + thispage + "/"
						+ pagenum + ") " + ChatColor.RED + "---------------");
				if (startindex > sm.size()) {
					player.sendMessage(ChatColor.RED + "Page does not exist!");
				}
				for (int i = startindex; i < endindex; i++) {
					if (startindex < sm.size()) {
						String name = sm.get(i).getDisplayName();
						int id = i + 1;
						player.sendMessage(ChatColor.RED + "" + id + ") "
								+ ChatColor.AQUA + name);
					}
				}
			} else {
				player.sendMessage(ChatColor.RED + "Too many arguments!");
			}
		}
		return true;
	}
}
