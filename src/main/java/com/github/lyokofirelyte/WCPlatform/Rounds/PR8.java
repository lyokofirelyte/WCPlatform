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

public class PR8 extends PlatformRound {

	public PR8(WCPlatform i) {
		super(i);
	}
	
	@Override
	public void start() {
		
		long delay = 0L;
		Random rand = new Random();
		
		for (int x = 0; x < 5; x++){
			
			Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
			int pick = rand.nextInt(main.gameData.getRows().size()-1)+1;
			
			while (true){
				if (main.gameData.getRows().get(pick).get(0).getBlock().getType().equals(Material.WOOL)){
					main.pah.changeLater(Change.ROW, pick, Material.WOOL, 4, delay);
					grids.put(1, pick);
					break;
				}
				pick = rand.nextInt(main.gameData.getRows().size())+1;
			}
			
			while (true){
				if (main.gameData.getColumns().get(pick).get(0).getBlock().getType().equals(Material.WOOL)){
					main.pah.changeLater(Change.COLUMN, pick, Material.WOOL, 4, delay);
					grids.put(2, pick);
					break;
				}
				pick = rand.nextInt(main.gameData.getColumns().size())+1;
			}
			
			delay+= 40L;
			
			main.pah.changeLater(Change.ROW, grids.get(1), Material.WOOL, 14, delay);
			main.pah.changeLater(Change.COLUMN, grids.get(2), Material.WOOL, 14, delay);
			
			delay+= 40L;
			
			main.pah.changeLater(Change.ROW, grids.get(1), Material.AIR, 0, delay);
			main.pah.changeLater(Change.COLUMN, grids.get(2), Material.AIR, 0, delay);
		}
		
		delay+= 60L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	public void restate(){
		
		main.gMsg("&bGood job! Time for a surprise!");
		
		long delay = 0L;  
		
		for (int y = 0; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.WOOL, 0, delay);
			delay+= 2L;
		}
		
		delay+= 20L;
		
		for (Location l : main.gameData.getGridSeperators()){
			if (l.getBlock().getType().equals(Material.AIR)){
				main.pah.changeLater(l, Material.WOOL, 0, delay);
				delay+= 1L;
			}
		}
		
		delay+= 20L;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.NINE));
	}
}