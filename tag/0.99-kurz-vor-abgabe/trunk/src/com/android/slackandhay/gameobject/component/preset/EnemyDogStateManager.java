package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.GOStateManager;

public class EnemyDogStateManager extends GOStateManager{
	protected final int NUMBER_OF_STATES = 7;
	public EnemyDogStateManager(){
		stateTable = new GOState[NUMBER_OF_STATES] ;
		super.stateTable[0] = new GOState(GOState.StateType.IDLE, 		0, 10000);
		super.stateTable[1] = new GOState(GOState.StateType.WALKING, 	0, 400);
		super.stateTable[2] = new GOState(GOState.StateType.ATTACKING, 	0, 300);
		super.stateTable[3] = new GOState(GOState.StateType.BLOCKING, 	0, 400);
		super.stateTable[4] = new GOState(GOState.StateType.STRUCK, 	0, 200);
		super.stateTable[5] = new GOState(GOState.StateType.DEAD, 		6, 300);
		super.stateTable[6] = new GOState(GOState.StateType.DESTROYED, 	6, 400);
		super.stateTable[activeState].start(0);
	}
}