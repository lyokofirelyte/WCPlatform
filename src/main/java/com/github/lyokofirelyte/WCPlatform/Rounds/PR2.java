package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;

public class PR2 extends PlatformRound {

	public PR2(WCPlatform i) {
		super(i);
	}

	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		
		for (int x = 0; x < 8; x++){
			
			Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
			
			for (int y = 0; y < 6; y++){
				grids.put(y, rand.nextInt(64));
				main.pah.changeLater(Change.GRID, grids.get(y), Material.WOOL, 4, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 6; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.WOOL, 14, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 6; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.AIR, 0, delay);
			}
			
			delay+= 60L;
		}
				
		delay+= 40L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	public void restate(){
		
		main.gMsg("&bAlright, next time I won't take it easy on you.");
		
		long delay = 0L;
		
		for (int y = 0; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.WOOL, 0, delay);
			delay+= 2L;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.THREE));
	}
}