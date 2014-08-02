package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * 
 * this class represents the AI component used in the game, it is inherited
 * inside the presets for real functionality.
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 */
public abstract class GOComponentAI extends GOComponentControl {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentAI.class.getSimpleName();

	protected GOGameObject target;

	public GOComponentAI(final GOGameObject parent) {
		super(parent);
	}

	public void setTarget(final GOGameObject target) {
		this.target = target;
	}

}
