package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;

/**
 *         This component is used to generate sounds for the game objects
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public abstract class GOComponentSound extends GOComponent {

	protected GOState.StateType lastState;

	public GOComponentSound(final GOGameObject parent) {
		super(GOComponentType.SOUND, parent);
	}

	@Override
	public void update(final int dt) {

	}
}
