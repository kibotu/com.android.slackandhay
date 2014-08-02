package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.gameobject.component.GOComponentSound;
import com.android.slackandhay.sound.SoundEngine;
import com.android.slackandhay.sound.SoundEngine.Sounds;

public class PlayerComponentSound extends GOComponentSound{

	private static final String TAG = PlayerComponentSound.class.getSimpleName();

	public PlayerComponentSound(final GOGameObject parent){
		super(parent);
		Log.e(TAG, "Added Component Sound");
	}

	@Override
	public void update(final int dt) {
		
		final StateType activeState = parent.getStateMananger().getActiveState().getStateType();
		final StateType lastState =  parent.getStateMananger().getLastState().getStateType();
		if(activeState == lastState)
			return;

		switch (activeState) {
		case IDLE:
			break;
		case WALKING:
			break;
		case RUNNING:
			break;
		case ATTACKING:
			SoundEngine.getInstance().playSound(Sounds.HIT_CONCRETE);
			break;
		case BLOCKING:
			break;
		case STRUCK:
			break;
		case DEAD:
			break;
		case DESTROYED:
			break;
		}
	}
}
