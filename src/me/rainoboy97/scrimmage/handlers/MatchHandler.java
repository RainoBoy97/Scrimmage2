package me.rainoboy97.scrimmage.handlers;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.utils.PlayerUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class MatchHandler {

	private static TeamHandler th;

	public MatchHandler() {
		MatchHandler.th = Scrimmage.getTH();
	}

	private static boolean running = false;
	private static boolean played = false;

	private static void setRunning(boolean running) {
		MatchHandler.running = running;
		if(!running) setPlayed(true);
	}
	
	private static void setPlayed(boolean played) {
		MatchHandler.played = played;
	}

	public static boolean running() {
		return MatchHandler.running;
	}
	
	public static boolean played() {
		return MatchHandler.played;
	}

	public static void start() {
		setRunning(true);

		for (String p : th.getPlayersOnTeam(Team.RED)) {
			Player player = Bukkit.getPlayerExact(p);
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.SURVIVAL);
			
			for(String p2 : th.getPlayersOnTeam(Team.OBSERVER)) {
				player.hidePlayer(Bukkit.getPlayerExact(p2));
			}
		}

		for (String p : th.getPlayersOnTeam(Team.BLUE)) {
			Player player = Bukkit.getPlayerExact(p);
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.SURVIVAL);
			
			for(String p2 : th.getPlayersOnTeam(Team.OBSERVER)) {
				player.hidePlayer(Bukkit.getPlayerExact(p2));
			}
		}
	}

	public static void stop(Team winner) {
		setRunning(false);
		setPlayed(true);
		if (winner == null) {
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### " + ChatColor.GREEN + "The match was forced to end!" + ChatColor.DARK_GRAY + " #####");
		} else {
			String winnerName = th.getTeamName(winner);
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### " + ChatColor.GREEN + "The match ended!" + ChatColor.DARK_GRAY + " #####");
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### " + ChatColor.valueOf(winnerName) + winnerName + ChatColor.GREEN + " has won the game!" + ChatColor.DARK_GRAY + " #####");
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.CREATIVE);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);
			}
		}
	}
}
