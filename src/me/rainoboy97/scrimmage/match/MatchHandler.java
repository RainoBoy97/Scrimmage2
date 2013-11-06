package me.rainoboy97.scrimmage.match;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.TeamHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler.Team;
import me.rainoboy97.scrimmage.utils.PlayerUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class MatchHandler {

	private static TeamHandler th;

	public MatchHandler() {
		MatchHandler.th = Scrimmage.getTH();
	}

	private static MatchState state = MatchState.PREGAME;

	private static long starttime = 0;
	private static long endtime = 0;

	private static void setState(MatchState state) {
		MatchHandler.state = state;
	}

	public static boolean running() {
		return MatchHandler.state == MatchState.INGAME;
	}

	public static boolean played() {
		return MatchHandler.state == MatchState.ENDED
				|| MatchHandler.state == MatchState.CYCLING;
	}

	public static void start() {
		setState(MatchState.INGAME);
		starttime = System.currentTimeMillis();
		for (String p : th.getPlayersOnTeam(Team.RED)) {
			Player player = Bukkit.getPlayerExact(p);
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.SURVIVAL);
			for (String p2 : th.getPlayersOnTeam(Team.OBSERVER)) {
				player.hidePlayer(Bukkit.getPlayerExact(p2));
			}
		}

		for (String p : th.getPlayersOnTeam(Team.BLUE)) {
			Player player = Bukkit.getPlayerExact(p);
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.SURVIVAL);
			for (String p2 : th.getPlayersOnTeam(Team.OBSERVER)) {
				player.hidePlayer(Bukkit.getPlayerExact(p2));
			}
		}
	}

	public static void stop(Team winner) {
		setState(MatchState.ENDED);
		endtime = System.currentTimeMillis();
		if (winner == null) {
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### "
					+ ChatColor.GREEN + "The match was forced to end!"
					+ ChatColor.DARK_GRAY + " #####");
		} else {
			String winnerName = th.getTeamName(winner);
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### "
					+ ChatColor.GREEN + "The match ended! "
					+ ChatColor.valueOf(winnerName)
					+ StringUtils.capitalize(winnerName.toLowerCase())
					+ ChatColor.GREEN + " has won the game!"
					+ ChatColor.DARK_GRAY + " #####");
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerUtils.clear(player);
			player.setGameMode(GameMode.CREATIVE);

			for (Player p : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);
			}
		}
	}

	public static long getTime() {
		System.out.println(System.currentTimeMillis());
		System.out.println(starttime);
		System.out.println(endtime);
		if (running())
			return System.currentTimeMillis() - starttime;
		else if (played())
			return endtime - starttime;
		else
			return 0;
	}

	public enum MatchState {
		PREGAME, INGAME, ENDED, CYCLING;
	}

}
