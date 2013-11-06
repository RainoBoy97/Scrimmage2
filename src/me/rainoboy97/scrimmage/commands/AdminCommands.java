package me.rainoboy97.scrimmage.commands;

import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.ScrimMapHandler;
import me.rainoboy97.scrimmage.handlers.ScrimMatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.match.ScrimMap;
import me.rainoboy97.scrimmage.utils.LookupUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}
		Player player = (Player) sender;

		if (!player.isOp()) {
			Scrimmage.msg(player, ChatColor.RED
					+ "Only OP's can use this command!");
			return true;
		}

		// ADMIN

		if (cmd.getName().equalsIgnoreCase("adminchat")) {
			if (args.length == 0) {
				Scrimmage.msg(player, ChatColor.RED + "/a <message>");
				return true;
			}
			String message = StringUtils.join(args, " ", 0, args.length);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage(ChatColor.GRAY
							+ "["
							+ ChatColor.GOLD
							+ "A"
							+ ChatColor.GRAY
							+ "] "
							+ Scrimmage.getPrefix(player)
							+ TeamHandler.getTeamColor(TeamHandler
									.getTeam(player)) + player.getName()
							+ ChatColor.WHITE + ": " + message);
				}
			}
			Scrimmage.logChat("[A] " + player.getName() + ": " + message);
		}

		// START
		/*
		 * if (cmd.getName().equalsIgnoreCase("start")) { int seconds = 30; if
		 * (args.length != 0) { try { seconds = Integer.parseInt(args[0]); }
		 * catch (Exception e) { player.sendMessage("/start <seconds>"); return
		 * true; } } if (MatchHandler.running()) { Scrimmage.msg(player,
		 * ChatColor.RED + "Match already started!"); return true; } if
		 * (MatchHandler.played()) { Scrimmage.msg(player, ChatColor.RED +
		 * "Match already played, can not be resumed!"); return true; }
		 * Scrimmage.msg(player, ChatColor.GREEN + "Started countdown (" +
		 * ChatColor.RED + seconds + ChatColor.GREEN + ")");
		 * CountdownHandler.startCountdown(seconds); }
		 */

		// LOAD MATCH
		if (cmd.getName().equalsIgnoreCase("setmatch")) {
			if (args.length > 0) {
				List<String> arg = new ArrayList<String>();
				for (String arg2 : args) {
					arg.add(arg2);
				}
				StringBuilder msg = new StringBuilder();
				for (String s : arg) {
					msg.append(" " + s);
				}
				String message = msg.toString().trim();
				int count = LookupUtils.countMap(message);
				if (count == 1) {
					ScrimMap m = LookupUtils.getMap(message);
					player.sendMessage(ChatColor.GREEN + "Loading "
							+ m.getDisplayName() + "...");
					ScrimMatchHandler.setMatch(m);
					ScrimMatchHandler.getCurrentMatch().loadMatch();
					ScrimMatchHandler.getCurrentMatch().teleportPlayers();
					player.sendMessage(ChatColor.GREEN + "Done loading "
							+ m.getDisplayName() + "!");
				} else if (count > 1) {
					sender.sendMessage(ChatColor.AQUA + "" + count
							+ ChatColor.GREEN + " maps matched query "
							+ ChatColor.AQUA + message);
				} else {
					sender.sendMessage(ChatColor.RED + "No maps matched query "
							+ ChatColor.AQUA + message);
				}
			} else {
				player.sendMessage(ChatColor.RED + "Not enough arguments!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("reloadmaps")) {
			ScrimMapHandler.clean();
			ScrimMapHandler.loadMaps();
			player.sendMessage(ChatColor.GREEN + "Maps reloaded!");
		}

		// END
		if (cmd.getName().equalsIgnoreCase("end")) {
			if (!ScrimMatchHandler.isRunning()) {
				Scrimmage.msg(player, ChatColor.RED + "No match running!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN
					+ "Force stopped the current match!");
		}

		// PVP
		if (cmd.getName().equalsIgnoreCase("pvp")) {
			Scrimmage.pvp = !Scrimmage.pvp;
			String toggle = ChatColor.GREEN + "on";
			if (!Scrimmage.pvp) {
				toggle = ChatColor.RED + "off";
			}
			Scrimmage.msg(player, ChatColor.GRAY + "PvP is now " + toggle
					+ ChatColor.GRAY + "!");
		}
		return true;
	}
}
