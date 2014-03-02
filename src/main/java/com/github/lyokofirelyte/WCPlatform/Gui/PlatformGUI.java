package com.github.lyokofirelyte.WCPlatform.Gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;
import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Rounds.Round;

public class PlatformGUI extends WCGui {
	
	private WCPlatform main;
	
	public PlatformGUI(WCPlatform main){
		
		super(9, "&3pLatform");
		this.main = main;
	}
	
	@Override
	public void create(){
		
		addButton(0, InventoryManager.createItem("&aJoin", new String[] { "&2Join the game!" }, Material.INK_SACK, 1, 2));
		addButton(1, InventoryManager.createItem("&cLeave", new String[] { "&4Leave a game" }, Material.INK_SACK, 1, 1));
		addButton(2, InventoryManager.createItem("&dStart Game!", new String[] { "&bBegin the fun." }, Material.INK_SACK, 1, 3));
		addButton(3, InventoryManager.createItem("&eTp to Arena", new String[] { "&eStaff debug tp" }, Material.INK_SACK, 1, 5));
		addButton(4, InventoryManager.createItem("&eReset Center", new String[] { "&6Reset center" }, Material.INK_SACK, 1, 6));
		addButton(5, InventoryManager.createItem("&eDevirt Arena", new String[] { "&6Devirtualize..." }, Material.INK_SACK, 1, 7));
		addButton(6, InventoryManager.createItem("&cStop Game", new String[] { "&4Emergency stop" }, Material.INK_SACK, 1, 8));
		addButton(7, InventoryManager.createItem("&ePause / Unpause", new String[] { "&3Toggle game state" }, Material.INK_SACK, 1, 8));
		addButton(8, InventoryManager.createItem("&1Debug Arrangement", new String[] { "&3Hug's Testing Section" }, Material.INK_SACK, 1, 9));
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (slot){
		
			case 0:
				
				main.pah.join(p);
				break;
				
			case 1:
				
				main.pah.quit(p);
				break;
				
			case 2:
				
				if (permCheck(p, "wa.mod2")){
					if (main.gameData.getCenter() != null){
						main.pah.formArena(main.gameData.getCenter());
					} else {
						main.pah.formArena(p.getLocation());
					}
				}
				
				break;
				
			case 3:
				
				if (permCheck(p, "wa.staff")){
					p.teleport(main.gameData.getCenter());
				}
				
				break;
				
			case 4:
				
				if (permCheck(p, "wa.mod2")){
					main.gameData.setCenter(p.getLocation());
					main.msg(p.getName(), "&aArena reset at your current location.");
				}
				
				break;
				
			case 5:
				
				if (permCheck(p, "wa.mod2")){
					main.pah.devirt();
				}
				
				break;
				
			case 6:
				
				if (permCheck(p, "wa.mod2")){
					stop();
				}
				
				break;
				
			case 7:
				
				if (permCheck(p, "wa.mod2")){
					pause();
				}
				
				break;
				
			case 8:
				
				if (permCheck(p, "wa.admin")){
					main.gMsg("&4DEBUGGING GRID ACTIVATED");
					Location center = p.getLocation();
					int pX = (int) center.getX();
					int pY = (int) center.getY();
					int pZ = (int) center.getZ();
					int radius = 15;
					main.data.setDelay("debugDelay", 0L);
					List<Location> toForm = new ArrayList<Location>();
					
					for (int x = (pX - radius); x <= (pX + radius); x++) {
						for (int z = (pZ - radius); z <= (pZ + radius); z++) {
							toForm.add(new Location(center.getWorld(), x, pY, z));
						}
					}
					
					main.gameData.setArenaFullGrid(toForm);
					
					for (final Location l : toForm){
						l.getBlock().setType(Material.WOOL);
					}
					
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
					
					for (Location l : toForm){
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
							main.gameData.getGrids().get(grid).add(toForm.get((y+jump)-1));
						}
						grid++;
						bleh++;
						jump+= 4;
					}
					
					for (int x : columnWatch){
						for (final Location l : main.gameData.getRows().get(x)){
							main.gameData.getGridSeperators().add(l);
							l.getBlock().setType(Material.AIR);
						}
					}
					
					for (int x : columnWatch){
						for (final Location l : main.gameData.getColumns().get(x)){
								main.gameData.getGridSeperators().add(l);
								l.getBlock().setType(Material.AIR);
						}
					}
				}
				
				break;
		}
	}
	
	public void stop(){
		
		if (!main.gameData.isStopped()){
			main.gMsg("&cThe game has been queued to be stopped after this round.");
			main.gameData.setStopped(true);
		}
	}
	
	public void pause(){
		
		if (!main.gameData.isPaused()){
			main.gMsg("&cThe game has been queued to be paused after this round.");
			main.gameData.setPaused(true);
		} else {
			main.gMsg("&cThe game is resuming.");
			main.gameData.setPaused(false);
			for (Round r : Round.values()){
				if (Integer.parseInt(r.toString()) == main.gameData.getCurrentRound()+1){
					main.data.getRound(r).start();
				}
			}
		}
	}
	
	public boolean permCheck(Player p, String s){
		
		if (!p.hasPermission(s)){
			main.api.wcm.displayGui(p, new GuiMessage(main, "&c&lYou don't have permission!", 3, p, this));
			return false;
		}
		
		return true;
	}	
}