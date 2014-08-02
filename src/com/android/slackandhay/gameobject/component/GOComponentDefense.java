package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * A component of this type handles damage that is dealt by other gameObjects,
 * which means it literally decided what to do with it. It is thinkable that the
 * damage will simply be ignored while or even handed back in retaliation.
 * 
 * @author til
 * 
 */
public abstract class GOComponentDefense extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentDefense.class.getSimpleName();

	/**
	 * Has to be added to components after health has been added to the
	 * component list.
	 */

	public GOComponentDefense(final GOGameObject parent) {
		super(GOComponentType.DEFENSIVE, parent);
	}

	@Override
	abstract public void update(final int dt);
	
}
