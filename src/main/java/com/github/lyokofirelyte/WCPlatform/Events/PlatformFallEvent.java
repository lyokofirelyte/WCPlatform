package com.github.lyokofirelyte.WCPlatform.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.github.lyokofirelyte.WCAPI.WCEvent;

public class PlatformFallEvent extends WCEvent {
	
	private Player p;
	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();
	
	public PlatformFallEvent(Player player){
		p = player;
	}

	@Override
	public Player getPlayer() {
		return p;
	}

	@Override
	public void setPlayer(Player player) {
		p = player;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

    public HandlerList getHandlers() {
        return handlers;
    }
 
	public static HandlerList getHandlerList() {
        return handlers;
    }
}