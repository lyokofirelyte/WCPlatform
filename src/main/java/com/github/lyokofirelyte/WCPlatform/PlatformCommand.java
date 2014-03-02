package com.github.lyokofirelyte.WCPlatform;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCPlatform.Gui.PlatformGUI;

public class PlatformCommand {
	
	public WCPlatform main;
	
	public PlatformCommand(WCPlatform i){
		main = i;
	}

	@WCCommand(aliases = {"platform"}, help = "/platform", desc = "Platform root command")
	public void platform(Player p, String[] args){
		
		if (args.length == 0){
			main.api.wcm.displayGui(p, new PlatformGUI(main));
		} else if (p.hasPermission("wa.admin")){
			if (WCUtils.isInteger(args[0])){
				main.data.rounds.get(Integer.parseInt(args[0])).start();
			} else if (args[0].equalsIgnoreCase("save")){
				main.data.saveSystem();
			}
		}
	}
}