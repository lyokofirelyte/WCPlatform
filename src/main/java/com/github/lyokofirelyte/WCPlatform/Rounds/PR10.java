package com.github.lyokofirelyte.WCPlatform.Rounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
		
		main.gMsg("&4RUN!!!! &6&oFollow the trail!");
		main.pah.changeLater(Change.ROW, 1, Material.STAINED_GLASS, 11, delay);
		main.pah.changeLater(Change.ROW, 2, Material.STAINED_GLASS, 11, delay);
		main.pah.changeLater(Change.ROW, 1, Material.AIR, 0, 60L);
		main.pah.changeLater(Change.ROW, 2, Material.AIR, 0, 60L);
		main.data.setId("omega", 1);
		main.data.setId("omegaCheck", 30);
		
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
			public void run(){
				if (main.gameData.getRows().get(main.data.getId("omega")).get(main.data.getId("omegaCheck")).getBlock().getType().equals(Material.STAINED_GLASS)
				&& main.gameData.getRows().get(main.data.getId("omega")+1).get(main.data.getId("omegaCheck")).getBlock().getType().equals(Material.STAINED_GLASS)){
					main.data.setId("omega", main.data.getId("omega")+3);
					List<Location> toIter = new ArrayList<Location>();
					List<Location> toIter2 = new ArrayList<Location>();
					for (Location l : main.gameData.getRows().get(main.data.getId("omega"))){
						toIter.add(l);
					}
					for (Location l : main.gameData.getRows().get(main.data.getId("omega")+1)){
						toIter2.add(l);
					}
					if (main.data.getId("omegaCheck") == 30){
						Collections.reverse(toIter);
						Collections.reverse(toIter2);
						main.data.setId("omegaCheck", 0);
					} else {
						main.data.setId("omegaCheck", 30);
					}
					long delay = 0L;
					for (Location l : toIter){
						main.pah.changeLater(l, Material.STAINED_GLASS, 11, delay);
						delay+= 4L;
					}
					delay = 0L;
					for (Location l : toIter2){
						main.pah.changeLater(l, Material.STAINED_GLASS, 11, delay);
						delay+= 4L;
					}
					delay = 55L;
					for (Location l : toIter){
						main.pah.changeLater(l, Material.AIR, 0, delay);
						delay+= 4L;
					}
					delay = 0L;
					for (Location l : toIter2){
						main.pah.changeLater(l, Material.AIR, 0, delay);
						delay+= 4L;
					}
					if (main.data.getId("omega") >= 27){
						Bukkit.getScheduler().cancelTask(main.data.getId("omegaRun"));
						restate();
					}
				}
			}
		}, 0L, 2L);
		
		main.data.setId("omegaRun", id);
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