package com.github.lyokofirelyte.WCPlatform.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class PlatformGameData {

	public WCPlatform main;
	
	public PlatformGameData(WCPlatform i){
		main = i;
	}
	
	public Map<String, PlatformPlayer> activePlayers = new HashMap<String, PlatformPlayer>();
	public Map<Integer, List<Location>> grids = new HashMap<Integer, List<Location>>();
	public Map<Integer, List<Location>> rows = new HashMap<Integer, List<Location>>();
	public Map<Integer, List<Location>> columns = new HashMap<Integer, List<Location>>();
	public List<Location> outerRing = new ArrayList<Location>();
	public List<Location> arenaFullGrid = new ArrayList<Location>();
	public List<Location> instructionCube = new ArrayList<Location>();
	public List<Location> gridSeperators = new ArrayList<Location>();
	public List<String> startMessages = Arrays.asList(
			"&bWelcome to pLatform. The arena is forming. Please read the information.",
			"&bThe arena is a dynamic and adaptive platform.",
			"&aPlease direct your attention to the instruction cube in the center. %white",
			"&bWhen squares turn &eyellow&b, they are about to turn &cred&b. %yellow",
			"&bWhen squares turn &cred&b, they are about to &fdissapear&b. %red",
			"&bFinally, if you fall from a dissapeared square, you will lose a life. %clear",
			"&apLatform rewards 10 points per round completed.",
			"&bIf you complete multiple in a row, you get a combo multiplier, up to 5.",
			"&aMore features will be explained later. For now, enjoy!");
	public Location center;
	public int currentRound = 0;
	public boolean paused;
	public boolean restart;
	public boolean stop;
	public boolean active;
	
	/** obtain information **/
	
	public int getCurrentRound(){
		return currentRound;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public boolean isRestarting(){
		return restart;
	}
	
	public boolean isStopped(){
		return stop;
	}
	
	public Location getCenter(){
		return center;
	}
	
	public Map<String, PlatformPlayer> getActivePlayers(){
		return activePlayers;
	}
	
	public List<String> getStartMessages(){
		return startMessages;
	}
	
	public List<Location> getOuterRing(){
		return outerRing;
	}
	
	public List<Location> getGridSeperators(){
		return gridSeperators;
	}
	
	public List<Location> getInstructionCube(){
		return instructionCube;
	}
	
	public Map<Integer, List<Location>> getGrids(){
		return grids;
	}
	
	public Map<Integer, List<Location>> getRows(){
		return rows;
	}
	
	public Map<Integer, List<Location>> getColumns(){
		return columns;
	}
	
	public List<Location> getArenaFullGrid(){
		return arenaFullGrid;
	}
	
	public PlatformPlayer getPlayer(String a){
		return activePlayers.get(a);
	}
	
	/** set information **/
	
	public void setCurrentRound(int a){
		currentRound = a;
	}
	
	public void setActive(boolean a){
		active = a;
	}
	
	public void setPaused(boolean a){
		paused = a;
	}
	
	public void setRestart(boolean a){
		restart = a;
	}
	
	public void setStopped(boolean a){
		stop = a;
	}
	
	public void setGridSeperators(List<Location> a){
		gridSeperators = a;
	}
	
	public void setGrids(Map<Integer, List<Location>> a){
		grids = a;
	}
	
	public void setRows(Map<Integer, List<Location>> a){
		rows = a;
	}
	
	public void setColumns(Map<Integer, List<Location>> a){
		columns = a;
	}
	
	public void setOuterRing(List<Location> a){
		outerRing = a;
	}
	
	public void setArenaFullGrid(List<Location> a){
		arenaFullGrid = a;
	}
	
	public void setInstructionCube(List<Location> a){
		instructionCube = a;
	}
	
	public void setCenter(Location a){
		center = a;
	}
	
	public boolean remPlayer(String a){
		
		if (activePlayers.containsKey(a)){
			activePlayers.remove(a);
			for (String s : getActivePlayers().keySet()){
				s(Bukkit.getPlayer(s), Bukkit.getPlayer(a).getDisplayName() + " &ahas left pLatform.");
			}
			return true;
		}
		
		return false;
	}
	
	public boolean addPlayer(String a){
		
		if (!activePlayers.containsKey(a)){
			if (main.data.getPlayer(a) == null){
				main.data.addPlayer(a);
			}
			activePlayers.put(a, main.data.getPlayer(a));
			for (String s : getActivePlayers().keySet()){
				s(Bukkit.getPlayer(s), Bukkit.getPlayer(a).getDisplayName() + " &ahas joined pLatform.");
			}
			return true;
		}

		return false;
	}
	
	public void setPlayers(Map<String, PlatformPlayer> a){
		activePlayers = a;
	}
}