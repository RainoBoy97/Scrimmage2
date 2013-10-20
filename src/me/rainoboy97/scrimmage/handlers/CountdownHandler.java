package me.rainoboy97.scrimmage.handlers;

import java.util.ArrayList;
import java.util.List;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.match.MapHandler;
import me.rainoboy97.scrimmage.match.MatchHandler;
import me.rainoboy97.scrimmage.match.SMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CountdownHandler {

    private static List<BukkitTask> tasks = new ArrayList<BukkitTask>();

    public static void startCountdown(final int seconds) {
        cancelAll();
        tasks.add(new BukkitRunnable() {
            int count = seconds;

            public void run() {
                if (count != 0) {
                    if (count % 5 == 0 || count <= 5 && count > 1) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in " + ChatColor.RED + count + ChatColor.GREEN + " seconds!");
                    } else if (count == 1) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in " + ChatColor.RED + count + ChatColor.GREEN + " second!");
                    }
                } else if (count == 0) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "##### " + ChatColor.GREEN + "The match has started!" + ChatColor.DARK_GRAY + " #####");
                    MatchHandler.start();
                    this.cancel();
                }
                count--;
            }
        }.runTaskTimer(Scrimmage.get(), 0L, 20L));

    }

    public static void startCycle(final int seconds) {
        cancelAll();

        tasks.add(new BukkitRunnable() {
            int count = seconds;
            SMap next = MapHandler.getNext();

            public void run() {
                if (count != 0) {
                    if (count % 5 == 0 || count <= 5 && count > 1) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Cycling to " + ChatColor.DARK_AQUA + next.getName() + ChatColor.GREEN + " in " + ChatColor.RED + count + ChatColor.GREEN + " seconds!");
                    } else if (count == 1) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Cycling to " + ChatColor.DARK_AQUA + next.getName() + ChatColor.GREEN + " in " + ChatColor.RED + count + ChatColor.GREEN + " second!");
                    }
                } else if (count == 0) {
                	Bukkit.broadcastMessage(ChatColor.GREEN + "Now playing " + ChatColor.DARK_AQUA + next.getName() + ChatColor.GREEN + "!");
                    this.cancel();
                }
                count--;
            }
        }.runTaskTimer(Scrimmage.get(), 0L, 20L));

    }

    public static void cancelAll() {
        for (BukkitTask bt : tasks) {
            bt.cancel();
        }
        tasks.clear();
    }

}
