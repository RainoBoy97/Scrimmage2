package me.rainoboy97.scrimmage.listeners;

import me.rainoboy97.scrimmage.Scrimmage;
import me.rainoboy97.scrimmage.handlers.MatchHandler;
import me.rainoboy97.scrimmage.handlers.TeamHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Compass {
	
	@SuppressWarnings("unused")
	private Scrimmage	plugin;
	private TeamHandler	th;
	@EventHandler
    public void onInteractEvent(PlayerInteractEvent event){   
            if(event.getAction().equals(Action.LEFT_CLICK_AIR)||equals(Action.LEFT_CLICK_BLOCK)); {
            	if(!MatchHandler.running() || th.isObserver(event.getPlayer() )) {
	                if(event.getPlayer().getItemInHand().getType() == Material.COMPASS){
	                    event.setCancelled(true);
	                    Block target = event.getPlayer().getTargetBlock(null, 200);
	                    float Yaw = event.getPlayer().getLocation().getYaw();
	                    float Pitch = event.getPlayer().getLocation().getPitch();
	                    Location loc = target.getLocation();
	                    loc.setY(loc.getY()+1);
	                    loc.setPitch(Pitch);
	                    loc.setYaw(Yaw);
	                    event.getPlayer().teleport(loc);
	                }
                }
            }
    }
}
