package com.github.lyokofirelyte.WCPlatform.Rounds;

import com.github.lyokofirelyte.WCPlatform.WCPlatform;

public abstract class PlatformRound {

	public WCPlatform main;
	
	public PlatformRound(WCPlatform i){
		main = i;
	}

	public abstract void start();
	
	public abstract void end();
}