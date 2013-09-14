package me.rainoboy97.scrimmage.commands;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.GRAY + "[G] " + Scrimmage.getPrefix(player) + th.getTeamColor(th.getTeam(player)) + player.getDisplayName() + ChatColor.WHITE + ": " + msg);
            }
            Scrimmage.logChat("[G] " + Scrimmage.getPrefix(player) + player.getName() + ": " + msg);

        }
        // JOIN
        if (cmd.getName().equalsIgnoreCase("join")) {
            Team team;
            boolean obs = false;
            if (args.length == 1) {
                team = Team.getTeam(args[0]);
            } else {
                team = th.getTeamWithLessPlayers();
            }
            if (team == Team.OBSERVER) {
                obs = true;
            }
            if (MatchHandler.played() && !obs) {
                Scrimmage.msg(player, ChatColor.RED + "Match is over - " + ChatColor.DARK_AQUA + "You cannot join a team until the next match!");
                return true;
            }
            if (team == null) {
                Scrimmage.msg(player, ChatColor.RED + "Invalid team!");
                return true;
            }
            if (th.isJoined(player) && !obs) {
                Scrimmage.msg(player, ChatColor.RED + "You have already joined a team!");
                return true;
            }
            th.addPlayer(player, team);
            Scrimmage.msg(player, ChatColor.GRAY + "You joined team " + th.getTeamColor(team) + StringUtils.capitalize(team.name().toLowerCase()));

        }

        // OPS
        if (cmd.getName().equalsIgnoreCase("ops")) {
            StringBuilder sb = new StringBuilder(ChatColor.GRAY + "Operators: ");
            for (OfflinePlayer op : Bukkit.getOperators()) {
                if (op.isOnline()) {
                    sb.append(ChatColor.GOLD + op.getName() + ChatColor.AQUA + ", ");
                    continue;
                }
                sb.append(ChatColor.GOLD + "" + ChatColor.ITALIC + op.getName() + ChatColor.AQUA + ", ");
            }
            String ops = sb.toString().trim().substring(0, sb.length() - 2);
            player.sendMessage(ops);
        }
        return true;
    }
}
