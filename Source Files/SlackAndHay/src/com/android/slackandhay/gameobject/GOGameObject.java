package com.android.slackandhay.gameobject;

import java.util.Arrays;

import com.android.slackandhay.UIDGenerator;
import com.android.slackandhay.gameobject.component.GOComponent;
import com.android.slackandhay.gameobject.component.GOComponentType;

/**
 * This class represents the most fundamental object we created to represent
 * objects inside the game, they can be extended with components
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public abstract class GOGameObject {

	@SuppressWarnings("unused")
	private static final String TAG = GOGameObject.class.getSimpleName();
	protected final int UID = UIDGenerator.getNewUID();
	/**
	 * contains a GOComponent of each possible type or <code>null</code>.
	 * Component index is ComponentType.type.ordinal().
	 */
	protected GOComponent[] components = new GOComponent[GOComponentType.values().length];
	protected GOStateManager stateManager;

	/**
	 * Please inherit me in the Factory class and always call super there!
	 * 
	 * @param stateManager
	 */
	protected GOGameObject(final GOStateManager stateManager) {
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
	public void update(final int dt) {
		// updating state manager so that the newest state is active when the
		// components are called.
		stateManager.update(dt);
		final int componentsCount = components.length;

		// iterating through the components; mind their
		// order!components[type.ordinal()]
		for (int i = 0; i < componentsCount; i++) {
			// if component isnt added
			if (components[i] == null) {
				continue;
			}
			components[i].update(dt);
		}
	}

	/**
	 * returns the uid of this game object
	 * 
	 * @return the uid
	 */
	public int getUID() {
		return UID;
	}

	/**
	 * returns the state manager of this object
	 * 
	 * @return state manager of this object
	 */
	public GOStateManager getStateMananger() {
		return stateManager;
	}

	/**
	 * Returns <code>true</code> if this gameObject has a component of the type
	 * given.
	 * 
	 * @param type
	 *            the type in question
	 * @return <code>true</code> if this gameObject has a component of the type
	 *         <code>type</code>.
	 */
	public boolean hasComponentOfType(final GOComponentType type) {
		return components[type.ordinal()] != null;
	}

	// TODO make sense? will require casting to access the component's
	// type-specific properties...
	public GOComponent getComponent(final GOComponentType type) {
		return components[type.ordinal()];
	}

	/**
	 * Adds a GOComponent to this gameObject. If a component of the same
	 * ComponentType is already present, it will be replaced.
	 * 
	 * @param component
	 *            the component to be added. <code>null</code> will effect no
	 *            changes; to remove a component, use {@link #removeComponent}.
	 * @return the replaced component; <code>null</code> means no replacement
	 *         took place.
	 */
	public GOComponent addComponent(final GOComponent component) {
		// TODO ok?
		if (component == null)
			return null;
		final GOComponentType type = component.getType();
		final GOComponent oldComponent = components[type.ordinal()];
		components[type.ordinal()] = component;
		return oldComponent;
	}

	/**
	 * Removes the component of the given type from this gameObject. Since the
	 * gameObject can have at most one component of any type, there is no
	 * ambiguity in which component to remove. If this gameObject has no such
	 * component, nothing will be removed.
	 * 
	 * @param type
	 *            The
	 *            {@link com.android.slackandhay.gameobject.component.GOComponent.ComponentType
	 *            ComponentType} type of the componnent to remove.
	 *            <code>null</code> will effect no changes.
	 * @return The GOComponent that was removed. <code>null</code> means nothing
	 *         was removed.
	 */
	public GOComponent removeComponent(final GOComponentType type) {
		if (type == null)
			return null;
		final GOComponent oldComponent = components[type.ordinal()];
		components[type.ordinal()] = null;
		return oldComponent;
	}

	// TODO to discuss?
	/**
	 * @deprecated handled in defensive component
	 * @param damage
	 * @param attacker
	 */
	@Deprecated
	public void takeDamage(final int damage, final GOGameObject attacker) {

	}

	/**
	 * @deprecated handled in inventory component (if it ever gets made)
	 * @param object
	 */
	@Deprecated
	public void gainObject(final GOGameObject object) {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + UID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GOGameObject)) {
			return false;
		}
		GOGameObject other = (GOGameObject) obj;
		if (UID != other.UID) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
		/*
		 * return "GOGameObject [UID=" + UID + ", components=" // uses
		 * StringBuilder: //+ Arrays.toString(components) + ", stateManager=" +
		 * stateManager + "]";
		 */
	}

}
