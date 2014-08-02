package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.GOStateManager;

public class TargetPointStateManager extends GOStateManager{
	protected final int NUMBER_OF_STATES = 1;
	public TargetPointStateManager(){
		stateTable = new GOState[NUMBER_OF_STATES] ;
		//The idle duration has to be long, so that it can be easily animated...
		super.stateTable[0] = new GOState(GOState.StateType.IDLE, 		0, 10000);
		super.stateTable[activeState].start(0);
	}
}
