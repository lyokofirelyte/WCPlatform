package com.github.lyokofirelyte.WCPlatform;

import static com.github.lyokofirelyte.WCAPI.WCUtils.s2;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCNode;
import com.github.lyokofirelyte.WCPlatform.Data.PlatformData;
import com.github.lyokofirelyte.WCPlatform.Data.PlatformGameData;
import com.github.lyokofirelyte.WCPlatform.Events.PlatformEventHandler;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR0;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR1;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR10;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR2;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR3;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR4;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR5;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR6;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR7;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR8;
import com.github.lyokofirelyte.WCPlatform.Rounds.PR9;
import com.github.lyokofirelyte.WCPlatform.Rounds.PlatformRound;

public class WCPlatform extends WCNode {
	
	public WCAPI api;
	public PlatformData data;
	public PlatformGameData gameData;
	public PlatformActionHandler pah;
	public FireworkShenans fw;
	public PluginManager pm;
	
	@Override
	public void onEnable(){
		
		pm = getServer().getPluginManager();
		Plugin WCAPI = pm.getPlugin("WCAPI");
		api = (WCAPI) WCAPI;
		data = new PlatformData(this);
		gameData = new PlatformGameData(this);
		pah = new PlatformActionHandler(this);
		fw = new FireworkShenans();
		load();
		reg();
	}
	
	@Override
	public void onDisable(){
		data.saveSystem();
	}
	
	public void reg(){
		
		pm.registerEvents(new PlatformEventHandler(this), this);
		api.reg.registerCommands(data.getCommandClasses(), this);
		
		List<PlatformRound> rounds = Arrays.asList(new PR0(this), new PR1(this), new PR2(this), new PR3(this), new PR4(this), new PR5(this), new PR6(this), new PR7(this), new PR8(this), new PR9(this), new PR10(this));
		int x = 0;
		
		for (PlatformRound r : rounds){
			data.getRounds().put(x, r);
			x++;
		}
	}
	
	public void load(){
		
		api.fm.registerConfig("platform/datacore");
		data.loadSystem(api.fm.getConfig("platform/datacore"));
	}
	
	public void gMsg(String m){
		for (String s : gameData.getActivePlayers().keySet()){
			s2(Bukkit.getPlayer(s), "&ap&bL &f// " + m);
		}
	}
	
	public void msg(String s, String m){
		s2(Bukkit.getPlayer(s), "&ap&bL &f// " + m);
	}
	
	public void playFirework(Location l, Type t, Color c){
		
		try {
			fw.playFirework(l.getWorld(), l, FireworkEffect.builder().with(t).withColor(c).build());
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}