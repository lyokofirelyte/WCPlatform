package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;

public class PR1 extends PlatformRound {

	public PR1(WCPlatform i) {
		super(i);
	}

	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		
		for (int x = 0; x < 7; x++){
			
			Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
			
			for (int y = 0; y < 4; y++){
				grids.put(y, rand.nextInt(64));
				main.pah.changeLater(Change.GRID, grids.get(y), Material.WOOL, 4, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 4; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.WOOL, 14, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 4; y++){
				main.pah.changeLater(Change.GRID, grids.get(y), Material.AIR, 0, delay);
			}
			
			delay+= 70L;
		}
				
		delay+= 40L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	public void restate(){
		
		main.gMsg("&bRound 1 was pretty easy eh?");
		
		long delay = 0L;
		
		for (int y = 0; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.WOOL, 0, delay);
			delay+= 2L;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.TWO));
	}
}