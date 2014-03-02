package com.github.lyokofirelyte.WCPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCPlatform.Data.PlatformPlayer;
import com.github.lyokofirelyte.WCPlatform.Events.RoundEndEvent;
import com.github.lyokofirelyte.WCPlatform.Rounds.Round;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class PlatformActionHandler {

	public WCPlatform main;
	
	public PlatformActionHandler(WCPlatform i){
		main = i;
	}
	
	public void join(Player p){
		
		if (!main.gameData.addPlayer(p.getName())){
			s(p, "You've already joined.");
		}
	}
	
	public void quit(Player p){
		
		if (!main.gameData.remPlayer(p.getName())){
			s(p, "You've already left.");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void devirt(){
		
		main.gMsg("&4&oThe arena is devirtualizing...");
		main.data.setDelay("devirt", 0L);
		main.gameData.setPlayers(new HashMap<String, PlatformPlayer>());
		main.gameData.setActive(false);
		
		for (final Location l : main.gameData.getArenaFullGrid()){
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
				
				public void run(){
			
					if (!l.getBlock().getType().equals(Material.AIR)){
					
						final Material m = l.getBlock().getType();
						l.getBlock().setType(Material.AIR);
						final String whileDelay = new String(System.currentTimeMillis() + "");
						main.data.setId(whileDelay, 0);
						main.data.setDelay(whileDelay, 0L);
						final FallingBlock b = l.getWorld().spawnFallingBlock(l, m, (byte)m.getId());
								
						int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
									
							public void run(){
								
								if (b.isDead()){
									main.getServer().getScheduler().cancelTask(main.data.getId(whileDelay));
								}
								
								if (!b.isDead() && b.getLocation().getY() <= l.getY()-10){
									l.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, m.getId());
									b.remove();
									main.getServer().getScheduler().cancelTask(main.data.getId(whileDelay));
								}
								
								main.data.setDelay(whileDelay, main.data.getDelay(whileDelay) + 1L);
							}
			
						}, 0L, main.data.getDelay(whileDelay));	
								
						if (main.data.getId(whileDelay) != 0){
							main.data.setId(whileDelay, id);
						}
					}
				}
			}, main.data.getDelay("devirt"));
			
			main.data.setDelay("devirt", main.data.getDelay("devirt") + 1L);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void formArena(Location center){
		
		Random rand = new Random();
		main.gameData.setCenter(center);
		int radius = 2;
		int pX = (int) center.getX();
		int pY = (int) center.getY();
		int pZ = (int) center.getZ();
		
		for (int x = 0; x < 2; x++){
			for (Location l : circle(center, 23+x, 1, true, false, 2)){
				l.getBlock().setTypeIdAndData(95, (byte) rand.nextInt(15), true);
				main.gameData.getOuterRing().add(l);
			}
		}
		
		for (int x = (pX - radius); x <= (pX + radius); x++) {
            for(int y = (pY - radius); y <= (pY + radius); y++) {
            	for (int z = (pZ - radius); z <= (pZ + radius); z++) {
            		main.gameData.getInstructionCube().add(new Location(center.getWorld(), x, y, z));
            	}
            }
		}
		
		main.gMsg("&bStarting in 10 seconds...");
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
			
			public void run(){
				startGame();
			}
			
		}, 200L);
	}
	
	public void startGame(){
		
		Random rand = new Random();
		
		for (String player : main.gameData.getActivePlayers().keySet()){
			Player p = Bukkit.getPlayer(player);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFlying(false);
			Location tp = main.gameData.getOuterRing().get(rand.nextInt(main.gameData.getOuterRing().size()-1));
			p.teleport(new Location(tp.getWorld(), tp.getX(), tp.getY()+2, tp.getZ()));
		}
		
		init();
	}
	
	public void timedReminder(){
		
		main.data.setDelay("startMessages", 0);
		
		for (final String msg : main.gameData.getStartMessages()){
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){

				public void run(){
					
					String[] literal = msg.split("%");
					main.gMsg(literal[0]);
					
					if (literal.length > 1){

						Color color = Color.BLACK;
						
						switch (literal[1]){
						
							case "white":
								color = Color.WHITE;
								break;
								
							case "yellow":
								color = Color.YELLOW;
								break;
								
							case "red":
								color = Color.RED;
								break;
								
							case "clear":
								color = Color.WHITE;
								break;
						}
						
						for (Location l : main.gameData.getInstructionCube()){
							main.playFirework(l, Type.BURST, color);
						}
					}
				}
				
			}, main.data.getDelay("startMessages"));	
			
			main.data.setDelay("startMessages", main.data.getDelay("startMessages") + 120L);
		}
	}
	
	@WCDelay(time = 200L)
	public void init(){
		
		timedReminder();
		Location center = main.gameData.getCenter();
		main.data.setArenaSet(true);
		
		final List<Location> toForm = new ArrayList<Location>();
		main.data.setDelay("form", 0L);
		
		int pX = (int) center.getX();
		int pY = (int) center.getY();
		int pZ = (int) center.getZ();
		int radius = 15;
		main.data.setId("formCounter", 0);
		
		for (int x = (pX - radius); x <= (pX + radius); x++) {
			for (int z = (pZ - radius); z <= (pZ + radius); z++) {
				toForm.add(new Location(center.getWorld(), x, pY, z));
			}
		}
		
		for (final Location l : toForm){
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
				
				public void run(){
					
					entityFormBlock(l, Material.WOOL);
					main.data.setId("formCounter", main.data.getId("formCounter") + 1);
					
					if (main.data.getId("formCounter") >= toForm.size()){
						phaseTwo(toForm);
					}
				}
				
			}, main.data.getDelay("form"));	
			
			main.data.setDelay("form", main.data.getDelay("form") + 1L);
		}
	}

	@SuppressWarnings("deprecation")
	public void entityFormBlock(final Location l, final Material m){
		
		final String whileDelay = new String(System.currentTimeMillis() + "");
		main.data.setId(whileDelay, 0);
		main.data.setDelay(whileDelay, 0L);
	
		final FallingBlock b = l.getWorld().spawnFallingBlock(new Location(l.getWorld(), l.getX(), l.getY()+10, l.getZ()), m, (byte) 0);
				
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
					
			public void run(){
				
				if (!b.isDead() && b.getLocation().getY() <= l.getY()+1){
					b.remove();
					l.getBlock().setTypeIdAndData(m.getId(), (byte) 0, true);;
					l.getWorld().playEffect(l, Effect.STEP_SOUND, m.getId());
					main.getServer().getScheduler().cancelTask(main.data.getId(whileDelay));
				}
				
				b.setVelocity(new Vector(0, -0.3, 0));
				main.data.setDelay(whileDelay, main.data.getDelay(whileDelay) + 1L);
			}

		}, 0L, main.data.getDelay(whileDelay));	
				
		if (main.data.getId(whileDelay) != 0){
			main.data.setId(whileDelay, id);
		}
	}
	
	public void phaseTwo(List<Location> arenaLocs){
		
		main.gameData.setArenaFullGrid(arenaLocs);
		
		List<Location> rowList = new ArrayList<Location>();
		List<Integer> columnWatch = Arrays.asList(4, 8, 12, 16, 20, 24, 28);
		int rowCounter = 0;
		int completeRows = 0;
		
		for (int x = 1; x < 32; x++){
			main.gameData.getColumns().put(x, new ArrayList<Location>());
		}
		
		for (int x = 0; x < 200; x++){
			main.gameData.getGrids().put(x, new ArrayList<Location>());
		}
		
		for (Location l : arenaLocs){
			rowCounter++;
			rowList.add(l);
			main.gameData.getColumns().get(rowCounter).add(l);
			if (rowCounter >= 31){
				completeRows++;
				main.gameData.getRows().put(completeRows, new ArrayList<Location>(rowList));
				rowList.clear();
				rowCounter = 0;
			}
		}
		
		List<Integer> g1 = Arrays.asList(1,2,3,34,65,64,63,32,33);
		
		int grid = 1;
		int jump = 0;
		int bleh = 1;
		
		for (int x = 0; x < 64; x++){
			if (bleh == 9){
				jump+= 92;
				bleh = 1;
			}
			for (int y : g1){
				main.gameData.getGrids().get(grid).add(arenaLocs.get((y+jump)-1));
			}
			grid++;
			bleh++;
			jump+= 4;
		}
		
		main.data.setDelay("columnWatch", 0);
		main.data.setDelay("rowWatch", 0);
		
		for (int x : columnWatch){
			for (final Location l : main.gameData.getRows().get(x)){
				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						
						public void run(){
							
							l.getBlock().setType(Material.AIR);
							main.gameData.getGridSeperators().add(l);
							main.playFirework(l, Type.BURST, Color.PURPLE);
						}
						
				 }, main.data.getDelay("rowWatch"));
				 
				 main.data.setDelay("rowWatch", main.data.getDelay("rowWatch") + 2L);
			}
		}
		
		for (int x : columnWatch){
			for (final Location l : main.gameData.getColumns().get(x)){
				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						
						public void run(){
							
							l.getBlock().setType(Material.AIR);
							main.gameData.getGridSeperators().add(l);
							main.playFirework(l, Type.BURST, Color.FUCHSIA);
						}
						
				 }, main.data.getDelay("columnWatch"));
				 
				 main.data.setDelay("columnWatch", main.data.getDelay("columnWatch") + 2L);
			}
		}
		
		final Location end = main.gameData.getRows().get(2).get(2);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
				
			public void run(){
					
				for (String s : main.gameData.getActivePlayers().keySet()){
					Player p = Bukkit.getPlayer(s);
					p.teleport(new Location(end.getWorld(), end.getX(), end.getY()+2, end.getZ()));
					p.setWalkSpeed((float) 0.2);
					p.setFlySpeed((float) 0.2);
				}
					
				for (Location l : main.gameData.getOuterRing()){
					l.getBlock().setType(Material.AIR);
				}
				
				main.pm.callEvent(new RoundEndEvent(null, Round.ONE));
			}
				
		}, main.data.getDelay("columnWatch"));
	}
	
	@SuppressWarnings("deprecation")
	public void change(Change type, final int section, final Material m, final int id){
		
		final long dly = System.currentTimeMillis();
		main.data.setDelay(dly + "", 0);
		
		switch (type){
		
			case ROW:
				
				for (final Location l : main.gameData.getRows().get(section)){
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						
						public void run(){
							l.getBlock().setTypeIdAndData(m.getId(), (byte) id, true);
						}
						
					}, main.data.getDelay(dly + ""));
					
					main.data.setDelay(dly + "", main.data.getDelay(dly + "") + 2L);
				}
				
				break;
				
			case COLUMN:
				
				for (final Location l : main.gameData.getColumns().get(section)){
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						
						public void run(){
							l.getBlock().setTypeIdAndData(m.getId(), (byte) id, true);
						}
						
					}, main.data.getDelay(dly + ""));
					
					main.data.setDelay(dly + "", main.data.getDelay(dly + "") + 2L);
				}
				
				break;
				
			case GRID:
				
				for (final Location l : main.gameData.getGrids().get(section)){
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						
						public void run(){
							l.getBlock().setTypeIdAndData(m.getId(), (byte) id, true);
						}
						
					}, main.data.getDelay(dly + ""));
					
					main.data.setDelay(dly + "", main.data.getDelay(dly + "") + 2L);
				}
				
				break;
		}
	}
	
	public void changeLater(final Change type, final int section, final Material m, final int id, final long delay){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
			
			public void run(){
				change(type, section, m, id);
			}
			
		}, delay);
	}
	
	@SuppressWarnings("deprecation")
	public void changeLater(final Location l, final Material m, final int id, final long delay){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
			
			public void run(){
				l.getBlock().setTypeIdAndData(m.getId(), (byte) id, true);
			}
			
		}, delay);
	}
}