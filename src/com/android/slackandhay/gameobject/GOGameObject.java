package com.android.slackandhay.gameobject;

import android.util.Log;

import com.android.slackandhay.SlackAndHayMain;

public abstract class GOGameObject {
	private int UID = SlackAndHayMain.getNewUID();
	protected GOComponent[] components;
	//protected int NUMBER_OF_COMPONENTS;
	protected GOStateManager stateManager;
	private static final String TAG = "GenericGameObject";
	
	/**
	 * Please inherit me in the Factory class and always call super there!
	 * @param stateManager
	 */
	protected GOGameObject(GOStateManager stateManager){
		this.stateManager = stateManager;
	}
	
	/**
	 * Updates all components which have been initialized in the subclasses of
	 * GOGameObject.
	 * 
	 * @param dt
	 *            specifies the amount of time, which has passed since the last
	 *            update.
	 * @param NUMBER_OF_COMPONENTS 
	 */
	public void update(int dt) {
		//updating state manager so that the newest state is active when the components are called.
		stateManager.update(dt);
		final int componentsCount = this.components.length;
		Log.e(TAG, "Compoents count: "+componentsCount);
		//iterating through the components; mind their order!
		for (int i = 0; i < componentsCount; i++) {
			Log.e(TAG,"Updating component: "+GOComponent.TAG);
			this.components[i].update(dt, this);
		}
	}

	public int getUID() {
		return UID;
	}
	
	public GOStateManager getStateMananger(){
		return this.stateManager;		
	}
}
