package me.rainoboy97.scrimmage.handlers;

import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.Scrimmage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScrimTeamHandler {

	static ScoreboardManager sm;
	static Scoreboard sb;
	static org.bukkit.scoreboard.Team red;
	static org.bukkit.scoreboard.Team blue;
	static org.bukkit.scoreboard.Team obs;
	static Objective obj;

	public static void loadTeams() {
		sm = Bukkit.getScoreboardManager();
		sb = sm.getNewScoreboard();
		obj = sb.registerNewObjective("playerhealth", "health");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj.setDisplayName(" / 20 " + ChatColor.RED + "♥");
		red = sb.registerNewTeam("Red");
		blue = sb.registerNewTeam("Blue");
		obs = sb.registerNewTeam("Observers");
		red.setAllowFriendlyFire(false);
		blue.setAllowFriendlyFire(false);
		obs.setAllowFriendlyFire(false);
		red.setDisplayName(ChatColor.DARK_RED + "Red Team");
		blue.setDisplayName(ChatColor.BLUE + "Blue Team");
		obs.setDisplayName(ChatColor.AQUA + "Observers");
		red.setPrefix(ChatColor.DARK_RED + "");
		blue.setPrefix(ChatColor.BLUE + "");
		obs.setPrefix(ChatColor.AQUA + "");
		red.setSuffix(ChatColor.WHITE + "");
		blue.setSuffix(ChatColor.WHITE + "");
		obs.setSuffix(ChatColor.WHITE + "");
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		for (Player p : onlinePlayers) {
			p.setScoreboard(sb);
		}
	}

	public static void loadScoreBoardPlayer(Player p) {
		p.setScoreboard(sb);
	}

	public static Team getTeam(Player player) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		if (red.getPlayers().contains(op))
			return Team.RED;
		else if (blue.getPlayers().contains(op))
			return Team.BLUE;
		else
			return Team.OBSERVER;
	}

	public static void addPlayer(Player player, Team team) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		removePlayer(player);
		switch (team) {
		case BLUE:
			blue.addPlayer(op);
			player.setDisplayName(Scrimmage.getPrefix(player) + ChatColor.BLUE
					+ player.getName() + ChatColor.WHITE);
			break;
		case RED:
			red.addPlayer(op);
			player.setDisplayName(Scrimmage.getPrefix(player)
					+ ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
			break;
		case OBSERVER:
			obs.addPlayer(op);
			player.setDisplayName(Scrimmage.getPrefix(player) + ChatColor.AQUA
					+ player.getName() + ChatColor.WHITE);
			break;
		default:
			obs.addPlayer(op);
			player.setDisplayName(Scrimmage.getPrefix(player) + ChatColor.AQUA
					+ player.getName() + ChatColor.WHITE);
			break;
		}
	}

	public static void removePlayer(Player player) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		if (red.getPlayers().contains(op)) {
			red.removePlayer(op);
		} else if (blue.getPlayers().contains(op)) {
			blue.removePlayer(op);
		} else if (obs.getPlayers().contains(op)) {
			obs.removePlayer(op);
		}
	}

	public static boolean isJoined(Player player) {
		if (getTeam(player) == Team.RED || getTeam(player) == Team.BLUE)
			return true;
		return false;
	}

	public static int count(Team team) {
		switch (team) {
		case RED:
			return red.getSize();
		case BLUE:
			return blue.getSize();
		case OBSERVER:
			return obs.getSize();
		default:
			return obs.getSize();
		}
	}

	public static List<String> getPlayersOnTeam(Team team) {
		List<String> res = new ArrayList<String>();
		switch (team) {
		case RED:
			for (OfflinePlayer op : red.getPlayers()) {
				res.add(op.getName());
			}
			break;
		case BLUE:
			for (OfflinePlayer op : blue.getPlayers()) {
				res.add(op.getName());
			}
			break;
		case OBSERVER:
			for (OfflinePlayer op : obs.getPlayers()) {
				res.add(op.getName());
			}
			break;
		default:
			for (OfflinePlayer op : obs.getPlayers()) {
				res.add(op.getName());
			}
			break;
		}
		return res;
	}

	public static Team getTeamWithLessPlayers() {
		int red = count(Team.RED);
		int blue = count(Team.BLUE);
		if (blue > red)
			return Team.RED;
		else
			return Team.BLUE;
	}

	public static String getTeamName(Team team) {
		switch (team) {
		case RED:
			return "Red Team";
		case BLUE:
			return "Blue Team";
		case OBSERVER:
			return "Observers";
		default:
			return "Undefined";
		}
	}

	public static ChatColor getTeamColor(Team team) {
		switch (team) {
		case RED:
			return ChatColor.DARK_RED;
		case BLUE:
			return ChatColor.BLUE;
		case OBSERVER:
			return ChatColor.AQUA;
		default:
			return ChatColor.YELLOW;
		}
	}

	public static boolean isObserver(Player player) {
		return getTeam(player) == Team.OBSERVER;
	}

	public enum Team {
		RED, BLUE, OBSERVER;
	}
}
