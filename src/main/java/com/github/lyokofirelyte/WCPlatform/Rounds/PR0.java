package com.github.lyokofirelyte.WCPlatform.Rounds;

import org.bukkit.Bukkit;

import com.github.lyokofirelyte.WCPlatform.WCPlatform;
import com.github.lyokofirelyte.WCPlatform.Data.PlatformPlayer;

public class PR0 extends PlatformRound {

	public PR0(WCPlatform i) {
		super(i);
	}

	@Override
	public void start(){
		
		if (main.gameData.isPaused() || main.gameData.isStopped()){
			return;
		}
		
		for (String s : main.gameData.getActivePlayers().keySet()){
			PlatformPlayer pp = main.gameData.getPlayer(s);
			main.msg(s, Bukkit.getPlayer(s).getDisplayName() + " &f-> &6" + pp.getLives() + " Lives&f,&6 " + pp.getScore() + " Score&f.");
		}
		
		main.gameData.setCurrentRound(main.gameData.getCurrentRound() + 1);
		main.gameData.setActive(true);
	}
	
	@Override
	public void end(){
		
		if (main.gameData.isPaused() || main.gameData.isStopped()){
			main.gMsg("&cThe game has been haulted!");
			Bukkit.getScheduler().cancelTasks(main);
			return;
		}
		
		for (String s : main.gameData.getActivePlayers().keySet()){
			
			PlatformPlayer pp = main.gameData.getPlayer(s);
			
			if (pp.getLives() > 0){
				pp.setScore(pp.getScore() + (10*pp.getCombo()));
				main.msg(s, "&aYou were awarded &3" + 10*pp.getCombo() + " &apoints.");
			} else {
				main.msg(s, "&7&oYou didn't get any points due to having no lives.");
			}
			
			if (pp.getCombo() < 5){
				pp.setCombo(pp.getCombo() + 1);
			}
			
			main.gameData.getActivePlayers().put(s, pp);
		}
		
		main.gameData.setActive(false);
		main.gMsg("&9Next round in 8 seconds!");
	}
}