package me.rainoboy97.scrimmage.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {

    public static void clear(Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setExhaustion(0);
        player.setFireTicks(0);
        player.setFlying(false);
        player.setSprinting(false);
        player.setLevel(0);
        player.setExp(0);
        player.setSaturation(5);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
    }

    public static void setListName(Player player, String newName) {
        if (newName.length() > 16) {
            newName = newName.substring(0, 16);
        }
        player.setPlayerListName(newName);
    }

}
