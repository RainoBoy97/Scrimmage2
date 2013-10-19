package me.rainoboy97.scrimmage.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class TeamHandler {

	private ScoreboardManager sm;
	private Scoreboard sb;
	private org.bukkit.scoreboard.Team red;
	private org.bukkit.scoreboard.Team blue;
	private org.bukkit.scoreboard.Team obs;
	private Objective obj;

	public void loadTeams() {
		sm = Bukkit.getScoreboardManager();
		sb = sm.getNewScoreboard();
		obj = sb.registerNewObjective("showhealth", "health");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj.setDisplayName("/ 20");
		red = sb.registerNewTeam("Red Team");
		blue = sb.registerNewTeam("Blue Team");
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
	
	public void loadScoreBoardPlayer(Player p) {
		p.setScoreboard(sb);
	}

	public Team getTeam(Player player) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		if (red.getPlayers().contains(op)) {
			return Team.RED;
		} else if (blue.getPlayers().contains(op)) {
			return Team.BLUE;
		} else {
			return Team.OBSERVER;
		}
	}

	public void addPlayer(Player player, Team team) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		removePlayer(player);
		switch (team) {
		case BLUE:
			blue.addPlayer(op);
			player.setDisplayName(ChatColor.BLUE + player.getName());
			break;
		case RED:
			red.addPlayer(op);
			player.setDisplayName(ChatColor.DARK_RED + player.getName());
			break;
		case OBSERVER:
			obs.addPlayer(op);
			player.setDisplayName(ChatColor.AQUA + player.getName());
			break;
		default:
			obs.addPlayer(op);
			player.setDisplayName(ChatColor.AQUA + player.getName());
			break;
		}
	}

	public void removePlayer(Player player) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(player.getName());
		if (red.getPlayers().contains(op)) {
			red.removePlayer(op);
		} else if (blue.getPlayers().contains(op)) {
			blue.removePlayer(op);
		} else if (obs.getPlayers().contains(op)) {
			obs.removePlayer(op);
		}
	}

	public boolean isJoined(Player player) {
		if (getTeam(player) == Team.RED || getTeam(player) == Team.BLUE) {
			return true;
		}
		return false;
	}

	public int count(Team team) {
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

	public List<String> getPlayersOnTeam(Team team) {
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

	public Team getTeamWithLessPlayers() {
		int red = count(Team.RED);
		int blue = count(Team.BLUE);
		if (blue > red)
			return Team.RED;
		else
			return Team.BLUE;
	}

	public String getTeamName(Team team) {
		return team.name();
	}

	public ChatColor getTeamColor(Team team) {
		return team.color();
	}

	public boolean isObserver(Player player) {
		return getTeam(player) == Team.OBSERVER;
	}

	public enum Team {
		RED("Red Team", ChatColor.DARK_RED), BLUE("Red Team", ChatColor.BLUE), OBSERVER(
				"Observers", ChatColor.AQUA);
		private ChatColor color;

		Team(String name, ChatColor color) {
			this.color = color;
		}

		public ChatColor color() {
			return color;
		}

		public static Team getTeam(String team) {
			for (Team t : values()) {
				if (t.name().toLowerCase().contains(team.toLowerCase()))
					return t;
			}
			return null;
		}
	}
}
