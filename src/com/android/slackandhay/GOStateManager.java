package com.android.slackandhay;

public class GOStateManager {
	private int activeStateX;
	private int activeStateY;
	GOState[][] stateTable;
	
	public GOState getActiveState(){
		return stateTable[activeStateX][activeStateY];
	}
}
