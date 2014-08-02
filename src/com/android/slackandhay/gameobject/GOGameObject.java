package com.android.slackandhay.gameobject;

import android.util.Log;

import com.android.slackandhay.SlackAndHayMain;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.gameobject.component.GOComponent;
import com.android.slackandhay.grid.GridLocation;

public abstract class GOGameObject {
	protected final int UID = SlackAndHayMain.getNewUID();
	/**
	 * contains a GOComponent of each possible type or <code>null</code>. Component index
	 * is ComponentType.type.ordinal().
	 */
	protected GOComponent[] components = new GOComponent[GOComponentType.values().length];
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
		//iterating through the components; mind their order!components[type.ordinal()]
		for (int i = 0; i < componentsCount; i++) {
			Log.e(TAG,"Updating component: "+GOComponent.TAG);
			this.components[i].update(dt);
		}
	}

	public int getUID() {
		return UID;
	}
	
	public GOStateManager getStateMananger(){
		return this.stateManager;		
	}
	
	/**
	 * Returns <code>true</code> if this gameObject has a component
	 * of the type given.
	 * 
	 * @param type	the type in question
	 * @return	<code>true</code> if this gameObject has a component
	 * of the type <code>type</code>.
	 */
	public boolean hasComponentOfType(GOComponentType type) {
		return components[type.ordinal()] != null;
	}
	
	// TODO make sense? will require casting to access the component's
	// type-specific properties...
	public GOComponent getComponent(GOComponentType type) {
		return components[type.ordinal()];
	}

	/**
	 * Adds a GOComponent to this gameObject. If a component of the same 
	 * ComponentType is already present, it will be replaced.
	 * 
	 * @param component	the component to be added. <code>null</code> will
	 * 					effect no changes; to remove a component, use
	 * 					{@link #removeComponent}.
	 * @return 	the replaced component; <code>null</code> means no
	 * 			replacement took place. 
	 */
	public GOComponent addComponent(GOComponent component) {
		// TODO ok?
		if (component == null) {
			return null;
		}
		GOComponentType type = component.getType();
		GOComponent oldComponent = components[type.ordinal()];
		components[type.ordinal()] = component;
		return oldComponent;
	}
	
	/**
	 * Removes the component of the given type from this gameObject. Since
	 * the gameObject can have at most one component of any type, there 
	 * is no ambiguity in which component to remove. If
	 * this gameObject has no such component, nothing will be removed.
	 * 
	 * @param type	The {@link com.android.slackandhay.gameobject.component.GOComponentType ComponentType}
	 * 				type of the componnent to remove. <code>null</code> will
	 * 				effect no changes.
	 * @return	The GOComponent that was removed. <code>null</code> means
	 * 			nothing was removed.
	 */
	public GOComponent removeComponent(GOComponentType type) {
		if (type == null) {
			return null;
		}
		GOComponent oldComponent = components[type.ordinal()];
		components[type.ordinal()] = null;
		return oldComponent;
	}


	
	//TODO to discuss?
	/**
	 * @deprecated handled in defensive component
	 * @param damage
	 * @param attacker
	 */
	public void takeDamage(int damage, GOGameObject attacker) {
		
	}
	
	/**
	 * @deprecated handled in inventory component (if it ever gets made)
	 * @param object
	 */
	public void gainObject(GOGameObject object) {
		
	}

}
