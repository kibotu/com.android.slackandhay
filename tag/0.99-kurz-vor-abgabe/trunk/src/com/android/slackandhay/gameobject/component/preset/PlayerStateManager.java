package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.GOStateManager;

public class PlayerStateManager extends GOStateManager{
	protected final int NUMBER_OF_STATES = 8;
	public PlayerStateManager(){
		stateTable = new GOState[NUMBER_OF_STATES] ;
		//The idle duration has to be long, so that it can be easily animated...
		super.stateTable[0] = new GOState(GOState.StateType.IDLE, 		0, 10000);
		super.stateTable[1] = new GOState(GOState.StateType.WALKING, 	0, 300); // seems ok
		super.stateTable[2] = new GOState(GOState.StateType.RUNNING, 	0, 150); // seems ok
		super.stateTable[3] = new GOState(GOState.StateType.ATTACKING, 	0, 300); // duration for hitting east & west != hitting south & north
		super.stateTable[4] = new GOState(GOState.StateType.BLOCKING, 	0, 200);
		super.stateTable[5] = new GOState(GOState.StateType.STRUCK, 	0, 200);
		super.stateTable[6] = new GOState(GOState.StateType.DEAD, 		7, 10000);
		super.stateTable[7] = new GOState(GOState.StateType.DESTROYED, 	7, 400);
		super.stateTable[activeState].start(0);
	}
}
