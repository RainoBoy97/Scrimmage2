package me.rainoboy97.scrimmage.commands;

import fr.aumgn.bukkitutils.command.args.CommandArgs;
import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.CountdownHandler;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {
    private TeamHandler th;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}
		Player player = (Player) sender;

		if (!player.isOp()) {
			player.sendMessage(ChatColor.RED + "Only OP's can use this command!");
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
					p.sendMessage(ChatColor.GRAY + "[A] " + th.getTeamColor(th.getTeam(player)) + player.getName() + ChatColor.WHITE + ": " + message);
				}
			}
		}


		// START
		if (cmd.getName().equalsIgnoreCase("start")) {
			int seconds = 30;
			if (args.length != 0) {
				try {
					seconds = Integer.parseInt(args[0]);
				} catch (Exception e) {
					player.sendMessage("/start <seconds>");
				}
			}
			if (MatchHandler.running()) {
				Scrimmage.msg(player, ChatColor.RED + "Match already started!");
				return true;
			}
			if (MatchHandler.played()) {
				Scrimmage.msg(player, ChatColor.RED + "Match already played, can not be resumed!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN + "Started countdown (" + ChatColor.RED + seconds + ChatColor.GREEN + ")");
			CountdownHandler.startCountdown(seconds);

		}

		// CANCEL
		if (cmd.getName().equalsIgnoreCase("cancel")) {
			Scrimmage.msg(player, ChatColor.GREEN + "Cancelled all running tasks!");
			CountdownHandler.cancelAll();
		}

        // PVP
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (Scrimmage.pvp = true) {
            Scrimmage.msg(player, ChatColor.AQUA + "PVP is now" + ChatColor.RED + "OFF");
            Scrimmage.pvp = false;
        }
            if (Scrimmage.pvp = false) {
                Scrimmage.msg(player, ChatColor.AQUA + "PVP is now" + ChatColor.GREEN + "ON");
                Scrimmage.pvp = true;
            }
        }


		// END
		if (cmd.getName().equalsIgnoreCase("end")) {
			if (!MatchHandler.running()) {
				Scrimmage.msg(player, ChatColor.RED + "No match running!");
				return true;
			}
			Scrimmage.msg(player, ChatColor.GREEN + "Force stopped the current match!");
			MatchHandler.stop(null);
		}
    return true;
	}
}

