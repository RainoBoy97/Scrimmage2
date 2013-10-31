package me.rainoboy97.scrimmage.commands;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.CountdownHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.match.MatchHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {

	private final TeamHandler th;

	public AdminCommands() {
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

		if (!player.isOp()) {
			Scrimmage.msg(player, ChatColor.RED
					+ "Only OP's can use this command!");
			return true;
		}

		// A
		if (cmd.getName().equalsIgnoreCase("a")) {
			if (args.length == 0) {
				Scrimmage.msg(player, ChatColor.RED + "/a <message>");
				return true;
			}
			String message = StringUtils.join(args, " ", 0, args.length);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "A"
							+ ChatColor.GRAY + "] "
							+ Scrimmage.getPrefix(player)
							+ th.getTeamColor(th.getTeam(player))
							+ player.getName() + ChatColor.WHITE + ": "
							+ message);
				}
			}
			Scrimmage.logChat("[A] " + player.getName() + ": " + message);
		}

		// START
		if (cmd.getName().equalsIgnoreCase("start")) {
			int seconds = 30;
			if (args.length != 0) {
				try {
					seconds = Integer.parseInt(args[0]);
				} catch (Exception e) {
					player.sendMessage("/start <seconds>");
					return true;
				}
			}
			if (MatchHandler.running()) {
				Scrimmage.msg(player, ChatColor.RED + "Match already started!");
				return true;
			}
			if (MatchHandler.played()) {
				Scrimmage.msg(player, ChatColor.RED
						+ "Match already played, can not be resumed!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN + "Started countdown ("
					+ ChatColor.RED + seconds + ChatColor.GREEN + ")");
			CountdownHandler.startCountdown(seconds);
		}

		// CYCLE
		if (cmd.getName().equalsIgnoreCase("cycle")) {
			int seconds = 30;
			if (args.length != 0) {
				try {
					seconds = Integer.parseInt(args[0]);
				} catch (Exception e) {
					player.sendMessage("/cycle <seconds>");
					return true;
				}
			}
			if (MapHandler.getNext() == null) {
				Scrimmage.msg(player, ChatColor.RED + "No map set!");
				return true;
			}
			if (MatchHandler.running()) {
				Scrimmage.msg(player, ChatColor.RED
						+ "Cant cycle while match is running!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN + "Started cycle ("
					+ ChatColor.RED + seconds + ChatColor.GREEN + ")");
			CountdownHandler.startCycle(seconds);
		}

		// CANCEL
		if (cmd.getName().equalsIgnoreCase("cancel")) {
			Scrimmage.msg(player, ChatColor.GREEN
					+ "Cancelled all running tasks!");
			CountdownHandler.cancelAll();
		}

		// END
		if (cmd.getName().equalsIgnoreCase("end")) {
			if (!MatchHandler.running()) {
				Scrimmage.msg(player, ChatColor.RED + "No match running!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN
					+ "Force stopped the current match!");
			MatchHandler.stop(null);
		}

		// LOADMAP
		if (cmd.getName().equalsIgnoreCase("setnext")) {
			if (args.length == 0) {
				Scrimmage.msg(player, ChatColor.RED + "/setnext <mapname>");
				return true;
			}
			String mapname = StringUtils.join(args, " ", 0, args.length);
			if (!MapHandler.mapExsists(mapname)) {
				Scrimmage.msg(player, ChatColor.RED + "Invalid map!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.RED + "Not done yet!");
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
