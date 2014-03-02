package com.github.lyokofirelyte.WCPlatform.Events;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Data.PlatformPlayer;
import com.github.lyokofirelyte.WCPlatform.Rounds.Round;

public class PlatformEventHandler implements Listener {

	public WCPlatform main;
	
	public PlatformEventHandler(WCPlatform i) {
		main = i;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		main.gameData.remPlayer(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		if (main.gameData.getCenter() != null && e.getPlayer().getWorld().equals(main.gameData.getCenter().getWorld())){
			if (main.gameData.getActivePlayers().containsKey(e.getPlayer().getName())){
				double fallY = main.gameData.getCenter().getY();
				if (e.getPlayer().getLocation().getY() <= (fallY-2)){
					Bukkit.getPluginManager().callEvent(new PlatformFallEvent(e.getPlayer()));
				}
			}
		}
	}
	
	@EventHandler
	public void onFall(PlatformFallEvent e){
		
		if (e.isCancelled()){
			return;
		}
		
		if (main.gameData.getArenaFullGrid().size() <= 0){
			
			for (Location l : main.gameData.getOuterRing()){
				if (!l.getBlock().getType().equals(Material.AIR)){
					e.getPlayer().teleport(new Location(l.getWorld(), l.getX(), l.getY()+2, l.getZ()));
					WCUtils.lowerEffects(l);
					break;
				}
			}
			
		} else {
			
			List<Location> locs = main.gameData.getArenaFullGrid();
		
			for (int x = 0; x < locs.size(); x++){
				Location l = locs.get((locs.size()-1)-x);
				if (!l.getBlock().getType().equals(Material.AIR)){
					e.getPlayer().teleport(new Location(l.getWorld(), l.getX(), l.getY()+2, l.getZ()));
					WCUtils.lowerEffects(l);
					break;
				}
			}
		}
		
		if (!main.gameData.isActive()){
			main.msg(e.getPlayer().getName(), "&7&oWatch out!");
			return;
		}
		
		PlatformPlayer pp = main.gameData.getPlayer(e.getPlayer().getName());
		
		if (pp.getLives() <= 0){
			main.msg(pp.getName(), "&cYou're out of lives. You can still play, but with no point rewards.");
		} else {
			pp.setLives(pp.getLives() - 1);
			pp.setCombo(1);
			main.msg(pp.getName(), "&cYou've lost a life. You now have &e" + pp.getLives() + " &cremaining.");
			main.gameData.getActivePlayers().put(pp.getName(), pp);
		}
	}
	
	@EventHandler
	public void onEnd(RoundEndEvent e){
		main.data.getRound(Round.ZERO).end();
		if (main.data.getRounds().containsKey(Integer.parseInt(e.getRound().toString()))){
			main.api.ls.callDelay(main, this, "newRound", e.getRound());
		}
	}
	
	@WCDelay(time = 160L)
	public void newRound(Round r){
		main.data.getRound(r).start();
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e){
		
		if (e.getEntity() instanceof Player && main.gameData.getActivePlayers().containsKey(((Player)e.getEntity()).getName())){
			e.setCancelled(true);
		}
	}
}