package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public abstract class GOComponentInput  extends GOComponent{

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentInput.class.getSimpleName();

	public GOComponentInput(final GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

	@Override
	public void update(final int dt) {

	}
}
