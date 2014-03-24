package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;

public class PR10 extends PlatformRound {

	public PR10(WCPlatform i) {
		super(i);
	}
	
	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		boolean a = true;
		
		main.gMsg("Enjoy this round and relax...");
		
		for (int y = 0; y < 10; y++){
		
			for (int x = 0; x < 31; x++){
				if (a){
					main.pah.changeLater(Change.ROW, x, Material.STAINED_GLASS, rand.nextInt(14), delay);
					main.pah.changeLater(Change.COLUMN, x, Material.STAINED_GLASS, rand.nextInt(14), delay);
				} else {
					main.pah.changeLater(Change.ROW, x, Material.GLASS, 0, delay);
					main.pah.changeLater(Change.COLUMN, x, Material.GLASS, 0, delay);
				}
				a = !a;
			}
		
			delay+= 60L;
		}

		delay+= 40L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	public void restate(){
		
		main.gMsg("&bEnd of game so far! Hope you enjoyed.");
		main.gameData.setActive(false);
		
		long delay = 60L;
		
		for (int y = 1; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.STAINED_GLASS, 4, delay);
			delay+= 2L;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		//main.pm.callEvent(new RoundEndEvent(null, Round.ELEVEN));
	}
}