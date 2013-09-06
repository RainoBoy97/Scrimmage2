package me.rainoboy97.scrimmage.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.utils.PlayerUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

public class TeamHandler {

	private Map<String, Team> players = new HashMap<String, Team>();

	public Team getTeam(Player player) {
		return players.get(player.getName());
	}

	public void addPlayer(Player player, Team team) {
		players.put(player.getName(), team);
		PlayerUtils.setListName(player, getTeamColor(getTeam(player)) + player.getName());
		TagAPI.refreshPlayer(player);
	}

	public void removePlayer(Player player) {
		players.remove(player.getName());
	}

	public int count(Team team) {
		int res = 0;
		for (String s : players.keySet()) {
			if (players.get(s) == team) {
				res++;
			}
		}
		return res;
	}

	public List<String> getPlayersOnTeam(Team team) {
		List<String> res = new ArrayList<String>();
		for (String s : players.keySet()) {
			if (players.get(s) == team) {
				res.add(s);
			}
		}
		return res;
	}
	
	public Team getTeamWithLessPlayers() {
		int red = count(Team.RED);
		int blue = count(Team.BLUE);
		if(blue > red) return Team.RED;
		else return Team.BLUE;
		
	}

	public String getTeamName(Team team) {
		return team.name();
	}

	public ChatColor getTeamColor(Team team) {
		return team.color();
	}

	public boolean isObserver(Player player) {
		return players.get(player.getName()) == Team.OBSERVER;
	}

	public enum Team {
		RED("Red", ChatColor.RED), BLUE("Blue", ChatColor.BLUE), OBSERVER("Observer", ChatColor.AQUA);
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
