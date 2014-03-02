package com.github.lyokofirelyte.WCPlatform.Data;

public class PlatformPlayer {

	private String name;
	
	public PlatformPlayer(String n){
		name = n;
	}
	
	public int lives = 4;
	public int score;
	public int combo = 1;
	
	/** obtain information **/
	
	public String getName(){
		return name;
	}
	
	public int getLives(){
		return lives;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getCombo(){
		return combo;
	}
	
	/** set information **/
	
	public void setLives(int a){
		lives = a;
	}
	
	public void setScore(int a){
		score = a;
	}
	
	public void setCombo(int a){
		combo = a;
	}
}