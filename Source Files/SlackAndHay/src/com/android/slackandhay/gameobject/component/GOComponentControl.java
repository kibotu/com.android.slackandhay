package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * 
 * this class represents how game objects are controlled, meaning right now that
 * they be controlled via an AI or the hardware controls (like the touch screen)
 * The ComponentAI and the ComponentInput inherit this class
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 */
public abstract class GOComponentControl extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentControl.class.getSimpleName();

	public GOComponentControl(final GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

}
