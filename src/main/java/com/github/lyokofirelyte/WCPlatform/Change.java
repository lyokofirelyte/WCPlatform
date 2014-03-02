package com.github.lyokofirelyte.WCPlatform;

public enum Change {

	ROW("row"),
	COLUMN("column"),
	GRID("grid");
	
	String changeType;
	
	Change(String ct){
		changeType = ct;
	}
}