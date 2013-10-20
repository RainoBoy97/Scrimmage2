package me.rainoboy97.scrimmage.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class ScrimmageRegion {

	private Location firstLocation;
	private Location secondLocation;
	private World world;

	public ScrimmageRegion(){

	}
	public ScrimmageRegion(ScrimmageRegion region){
		this.firstLocation = region.getPrimaryLocation();
		this.secondLocation = region.getSecondaryLocation();
		this.world = region.getWorld();
	}
	
	public ScrimmageRegion(Location first, Location second){
		this.firstLocation = first;
		this.secondLocation = second;
		this.world = firstLocation.getWorld();
	}

	public boolean isWithinRegion(Location l){
		int x1 = firstLocation.getBlockX();
		int x2 = secondLocation.getBlockX();

		int z1 = firstLocation.getBlockZ();
		int z2 = secondLocation.getBlockZ();

		int y1 = firstLocation.getBlockY();
		int y2 = secondLocation.getBlockY();

		int lx = l.getBlockX();
		int ly = l.getBlockY();
		int lz = l.getBlockZ();
		if (lx >= Math.min(x1, x2) & lx <= Math.max(x1, x2) & ly >= Math.min(y1, y2) & ly <= Math.max(y1, y2) & lz >= Math.min(z1, z2) & 
				lz <= Math.max(z1, z2)) return true;

		return false;
	}
	public boolean isWithinRegionIgnoreY(Location l){
		int x1 = firstLocation.getBlockX();
		int x2 = secondLocation.getBlockX();

		int z1 = firstLocation.getBlockZ();
		int z2 = secondLocation.getBlockZ();

		int lx = l.getBlockX();
		int lz = l.getBlockZ();

		if (lx >= Math.min(x1, x2) & lx <= Math.max(x1, x2) & lz >= Math.min(z1, z2) & 	lz <= Math.max(z1, z2)) return true;
		return false;
	}
	public boolean isWithinRegionAboveY(Location l){
		int x1 = firstLocation.getBlockX();
		int x2 = secondLocation.getBlockX();

		int z1 = firstLocation.getBlockZ();
		int z2 = secondLocation.getBlockZ();

		int y1 = firstLocation.getBlockY();
		int y2 = secondLocation.getBlockY();

		int lx = l.getBlockX();
		int ly = l.getBlockY();
		int lz = l.getBlockZ();
		if (lx >= Math.min(x1, x2) & lx <= Math.max(x1, x2) & ly >= Math.min(y1, y2) & lz >= Math.min(z1, z2) & 
				lz <= Math.max(z1, z2)) return true;

		return false;
	}
	public int calculteVolume(){
		return getWidth() * getHeight() * getDepth();
	}
	public Location calculateCenter(){
		int x = Math.min(firstLocation.getBlockX(), secondLocation.getBlockX()) + getWidth();
		int y = Math.min(firstLocation.getBlockY(), secondLocation.getBlockY()) + getHeight();
		int z = Math.min(firstLocation.getBlockZ(), secondLocation.getBlockZ()) + getDepth();
		return new Location(world, x, y, z);
	}
	public int getWidth(){
		return Math.abs(firstLocation.getBlockX() - secondLocation.getBlockX());
	}
	public int getDepth(){
		return Math.abs(firstLocation.getBlockZ() - secondLocation.getBlockZ());
	}
	public int getHeight(){
		return Math.abs(firstLocation.getBlockY() - secondLocation.getBlockY());
	}
	public World getWorld(){
		return this.world;
	}
	public void setPrimaryLocation(Location loc1){
		this.firstLocation = loc1;
		if (this.world == null) this.world = loc1.getWorld();
	}
	public void setSecondaryLocation(Location loc2){
		this.secondLocation = loc2;
		if (this.world == null) this.world = loc2.getWorld();
	}
	public Location getPrimaryLocation(){
		return this.firstLocation;
	}
	public Location getSecondaryLocation(){
		return this.secondLocation;
	}
}
