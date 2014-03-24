package com.github.lyokofirelyte.WCPlatform.Data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Rounds.PlatformRound;
import com.github.lyokofirelyte.WCPlatform.Rounds.Round;

public class PlatformData {

	public WCPlatform main;
	
	public PlatformData(WCPlatform i){
		main = i;
	}
	
	public Map<Integer, PlatformRound> rounds = new HashMap<Integer, PlatformRound>();
	public Map<String, PlatformPlayer> players = new HashMap<String, PlatformPlayer>();
	public Map<String, Long> delays = new HashMap<String, Long>();
	public Map<String, Integer> ids = new HashMap<String, Integer>();
	public boolean arenaSet = false;
	
	/** obtain information **/
	
	public Map<Integer, PlatformRound> getRounds(){
		return rounds;
	}
	
	public PlatformRound getRound(Round a){
		if (!a.toString().equals("0")){
			rounds.get(0).start();
		}
		return rounds.get(Integer.parseInt(a.toString()));
	}
	
	
	public Map<String, PlatformPlayer> getPlayers(){
		return players;
	}
	
	public Long getDelay(String a){
		return delays.get(a);
	}
	
	public Integer getId(String a){
		return ids.get(a);
	}
	
	public PlatformPlayer getPlayer(String a){
		
		if (players.containsKey(a)){
			return players.get(a);
		}
		
		return null;
	}
	
	public boolean isArenaSet(){
		return arenaSet;
	}
	

	
	/** set information **/
	
	public void setPlayers(Map<String, PlatformPlayer> a){
		players = a;
	}
	
	public void setDelay(String a, long b){
		delays.put(a, b);
	}
	
	public void setId(String a, int b){
		ids.put(a, b);
	}
	
	public boolean addPlayer(String a){
		
		if (!players.containsKey(a)){
			players.put(a, new PlatformPlayer(a));
			return true;
		}
		
		return false;
	}
	
	public boolean addPlayer(String a, PlatformPlayer b){
		
		if (!players.containsKey(a)){
			players.put(a, b);
			if (!main.api.fm.getConfig("platform/datacore").getStringList("Users").contains(a)){
				main.api.fm.getConfig("platform/datacore").getStringList("Users").add(a);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean remPlayer(String a, boolean del){
		
		if (players.containsKey(a)){
			players.remove(a);
			if (del && main.api.fm.getConfig("platform/datacore").getStringList("Users").contains(a)){
				main.api.fm.getConfig("platform/datacore").getStringList("Users").remove(a);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean remPlayer(PlatformPlayer a){
		
		String toRem = "%none%";
		
		if (players.containsValue(a)){
			for (String p : players.keySet()){
				if (players.get(p).equals(p)){
					toRem = p;
				}
			}
		}
		
		if (!toRem.equals("%none%")){
			players.remove(toRem);
			return true;
		}
		
		return false;
	}
	
	public void setArenaSet(boolean a){
		arenaSet = a;
	}

	
	/** manager **/
	
	public void loadSystem(YamlConfiguration yaml){

		try {
			String[] l = yaml.getString("ArenaCenter").split(" ");
			setArenaSet(yaml.getBoolean("ArenaSet"));
			main.gameData.setCenter(new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3])));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveSystem(){
		
		Location l = main.gameData.getCenter();
		main.api.fm.getConfig("platform/datacore").set("ArenaSet", isArenaSet());
		main.api.fm.getConfig("platform/datacore").set("ArenaCenter", l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
		main.api.fm.saveConfig("platform/datacore");
	}
}