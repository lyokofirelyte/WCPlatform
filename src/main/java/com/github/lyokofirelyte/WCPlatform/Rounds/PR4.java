package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import com.github.lyokofirelyte.WCPlatform.Change;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;

public class PR4 extends PlatformRound {

	public PR4(WCPlatform i) {
		super(i);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void start() {
		
		long delay = 0L;
		long delay2 = 0L;
		Random rand = new Random();
		
		for (List<Location> list : main.gameData.getColumns().values()){
			if (list.get(0).getBlock().getType().equals(Material.AIR)){
				for (final Location l : list){
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ 
						public void run(){
							l.getBlock().setType(Material.WOOL);
							l.getWorld().playEffect(l, Effect.STEP_SOUND, Material.WOOL.getId());
						}
					}, delay);
					delay+= 1L;
				}
			}
		}
		
		for (List<Location> list : main.gameData.getRows().values()){
			if (list.get(0).getBlock().getType().equals(Material.AIR)){
				for (final Location l : list){
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ 
						public void run(){
							l.getBlock().setType(Material.WOOL);
							l.getWorld().playEffect(l, Effect.STEP_SOUND, Material.WOOL.getId());
						}
					}, delay2);
					delay2+= 1L;
				}
			}
		}
		
		delay+= 220L;
		
		for (int x = 0; x < 4; x++){
			
			Map<Integer, Integer> grids = new HashMap<Integer, Integer>();
			
			for (int y = 0; y < 2; y++){
				int pick = rand.nextInt(30);
				while (grids.containsValue(pick)){
					pick = rand.nextInt(30);
					if (!grids.containsValue(pick)){
						break;
					}
				}
				grids.put(y, pick);
				main.pah.changeLater(Change.ROW, grids.get(y), Material.WOOL, 4, delay);
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.WOOL, 4, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 2; y++){
				main.pah.changeLater(Change.ROW, grids.get(y), Material.WOOL, 14, delay);
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.WOOL, 14, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 2; y++){
				main.pah.changeLater(Change.ROW, grids.get(y), Material.AIR, 0, delay);
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.AIR, 0, delay);
			}
			
			delay+= 40L;
			grids = new HashMap<Integer, Integer>();
			
			for (int y = 0; y < 2; y++){
				int pick = rand.nextInt(30);
				while (grids.containsValue(pick)){
					pick = rand.nextInt(30);
					if (!grids.containsValue(pick)){
						break;
					}
				}
				grids.put(y, pick);
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.WOOL, 4, delay);
				main.pah.changeLater(Change.ROW, grids.get(y), Material.WOOL, 4, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 2; y++){
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.WOOL, 14, delay);
				main.pah.changeLater(Change.ROW, grids.get(y), Material.WOOL, 14, delay);
			}
			
			delay+= 40L;
			
			for (int y = 0; y < 2; y++){
				main.pah.changeLater(Change.COLUMN, grids.get(y), Material.AIR, 0, delay);
				main.pah.changeLater(Change.ROW, grids.get(y), Material.AIR, 0, delay);
			}
			
			delay+= 20L;
		}
				
		delay+= 40L;
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ restate(); }}, delay);
	}
	
	@SuppressWarnings("deprecation")
	public void restate(){
		
		main.gMsg("&aGreen &3squares will provide a run boost to the entire group...");
		
		long delay = 0L;
		main.gameData.setActive(false);
		
		for (Location l : main.gameData.getGridSeperators()){
			if (!l.getBlock().getType().equals(Material.AIR)){
				main.pah.changeLater(l, Material.AIR, 0, delay);
				l.getWorld().playEffect(l, Effect.STEP_SOUND, Material.WOOL.getId());
				delay+= 1L;
			}
		}
		
		for (int y = 0; y < 65; y++){
			main.pah.changeLater(Change.GRID, y, Material.ICE, 0, delay);
			delay+= 2L;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){ public void run(){ end(); }}, delay);
	}
	
	@Override
	public void end(){
		main.pm.callEvent(new RoundEndEvent(null, Round.FIVE));
	}
}