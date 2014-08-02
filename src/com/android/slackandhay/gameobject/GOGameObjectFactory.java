package com.android.slackandhay.gameobject;

import android.util.Log;

import com.android.slackandhay.gameobject.component.GOComponent;
import com.android.slackandhay.gameobject.component.GOComponentSound;
import com.android.slackandhay.sound.SoundEngine;
import com.android.slackandhay.sound.SoundEngine.Sounds;

/**
 * Creates Subclasses of the Components and the State Manager to create a new Game Object 
 * @author tom
 *
 */

public class GOGameObjectFactory {

	private static final String TAG = "GameObjectFactory";
	
	//############################
	//  PLAYER FACTORY
	//############################
	private class GOStateManagerPlayer extends GOStateManager{
		protected final int NUMBER_OF_STATES = 8;
		public GOStateManagerPlayer(){
			stateTable = new GOState[NUMBER_OF_STATES] ;
			//The idle duration has to be long, so that it can be easily animated... 
			super.stateTable[0] = new GOState(GOState.StateType.IDLE, 0, 10000);
			super.stateTable[1] = new GOState(GOState.StateType.WALKING, 0, 100);
			super.stateTable[2] = new GOState(GOState.StateType.RUNNING, 0, 100);
			super.stateTable[3] = new GOState(GOState.StateType.ATTACKING, 0, 100);
			super.stateTable[4] = new GOState(GOState.StateType.BLOCKING, 0, 100);
			super.stateTable[5] = new GOState(GOState.StateType.STRUCK, 0, 100);
			super.stateTable[6] = new GOState(GOState.StateType.DEAD, 7, 100);
			super.stateTable[7] = new GOState(GOState.StateType.DESTROYED, 7, 100);
			super.stateTable[activeState].start(0);
		}
	}
	private class GOPlayer extends GOGameObject{
		protected final int NUMBER_OF_COMPONENTS = 1;
		
		public GOPlayer() {
			super(new GOStateManagerPlayer());
			super.components = new GOComponent[NUMBER_OF_COMPONENTS];
			super.components[0] = new GOComponentSoundPlayer(this);
			
			Log.e(TAG, "Creating Player Object..." );
			//Additional Components....
		}
	}
	
	private class GOComponentSoundPlayer extends GOComponentSound{
		public GOComponentSoundPlayer(GOGameObject parent){
			super(parent);
			Log.e(TAG, "Added Component Sound");
		}
		@Override
		public void update(int dt) {
			Log.e(TAG, "Updating Sound Component...");
			if(parent.getStateMananger().getActiveState().getStateType() == GOState.StateType.ATTACKING){
				Log.e(TAG, "triggering attack sound");
				SoundEngine.getInstance().playSound(Sounds.HIT_CONCRETE);
			}
		}

	}
	
	public GOGameObject createPlayer(){
		return new GOPlayer();
	}
	
	//############################
	//  MONSTER 1 FACTORY
	//############################

}
