package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;

public class PR5 extends PlatformRound {

	public PR5(WCPlatform i) {
		super(i);
	}
	
	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
		
		for (int x = 0; x < 9; x++){
			
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
			
			delay+= 10L;
			
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
		
		main.gMsg("&bHope you didn't slip up! :D");
		
		long delay = 0L;
		boolean a = false;
		
		for (int y = 0; y < 65; y++){
			if (a){
				main.pah.changeLater(Change.GRID, y, Material.STAINED_CLAY, 0, delay);
			} else {
				main.pah.changeLater(Change.GRID, y, Material.WOOL, 0, delay);
			}
			a = !a;
			delay+= 2L;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.SIX));
	}
}