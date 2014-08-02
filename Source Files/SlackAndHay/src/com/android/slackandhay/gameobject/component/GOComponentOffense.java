package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridLocation;

/**
 * A component of this type is able to deal damage to other gameObjects.
 * What they do with the damage taken is up to their
 * {@link GOComponentDefense defensive} component.
 * 
 * @author til
 *
 */
public abstract class GOComponentOffense extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentOffense.class.getSimpleName();

	protected GridLocation target;

	public GOComponentOffense(final GOGameObject parent) {
		super(GOComponentType.OFFENSIVE, parent);
	}

	@Override
	public void update(final int dt) {
		// TODO Auto-generated method stub

	}

	public boolean locationIsInRange(final GridLocation location) {
		//TODO
		return true;
	}

	public boolean attack() {
		//TODO
		return false;
	}
	
	public boolean isInRange(GOGameObject target) {
		return false;
	}


}
