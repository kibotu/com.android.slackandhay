package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * 
 * This component is used to take inputs from touch screen to start player
 * actions
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 */
public abstract class GOComponentInput extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentInput.class.getSimpleName();

	public GOComponentInput(final GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

	@Override
	public void update(final int dt) {

	}
}
