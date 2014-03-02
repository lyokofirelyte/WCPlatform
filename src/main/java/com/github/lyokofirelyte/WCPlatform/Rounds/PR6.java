package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;

public class PR6 extends PlatformRound {

	public PR6(WCPlatform i) {
		super(i);
	}
	
	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
		
		for (int x = 0; x < 12; x++){
			
			for (int y = 0; y < 5; y++){
				int pick = rand.nextInt(64);
				while (grids.containsValue(pick)){
					pick = rand.nextInt(64);
					if (!grids.containsValue(pick)){
						break;
					}
				}
				grids.put(y, pick);
				main.pah.changeLater(Change.GRID, grids.get(y), Material.STAINED_CLAY, 4, delay);
			}
			
			delay+= 20L;
			
			for (int y = 0; y < 5; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.STAINED_CLAY, 14, delay);
			}
			
			delay+= 10L;
			
			for (int y = 0; y < 5; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.AIR, 0, delay);
			}
			
			delay+= 20L;
		}
		
		delay+= 40L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	public void restate(){
		
		main.gMsg("&bTime for a boss round! :D");
		
		long delay = 0L;
		
		for (int y = 0; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.OBSIDIAN, 0, delay);
			delay+= 2L;
		}
		
		delay+= 2L;
		
		for (Location l : main.gameData.getGridSeperators()){
			main.pah.changeLater(new Location(l.getWorld(), l.getX(), l.getY()+4, l.getZ()), Material.STAINED_GLASS, 11, delay);
		}
		
		delay+= 20L;
		
		for (Location l : main.gameData.getGridSeperators()){
			main.pah.changeLater(l, Material.AIR, 11, delay);
		}
		
		delay+= 20L;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.SEVEN));
	}
}