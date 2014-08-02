package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public abstract class GOComponentControl extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentControl.class.getSimpleName();

	public GOComponentControl(final GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

}
