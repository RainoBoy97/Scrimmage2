package me.rainoboy97.scrimmage.antigrief;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class crafting {
    @EventHandler
    public void event_craft(PlayerInteractEvent event) {
    if(!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
    if(!(event.getClickedBlock().getType() == Material.WORKBENCH)) return;
    event.setCancelled(true);
    event.getPlayer().openWorkbench(null, true);
} }
