package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.GOStateManager;

public class NonGridMemberStateManager  extends GOStateManager{
	protected final int NUMBER_OF_STATES = 1;
	public NonGridMemberStateManager(){
		stateTable = new GOState[NUMBER_OF_STATES] ;
		super.stateTable[0] = new GOState(GOState.StateType.IDLE, 0, 10000);
		super.stateTable[activeState].start(0);
	}
}
