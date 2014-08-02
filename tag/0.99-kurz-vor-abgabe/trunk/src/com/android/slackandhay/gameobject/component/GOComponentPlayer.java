package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public abstract class GOComponentPlayer  extends GOComponent{

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentPlayer.class.getSimpleName();

	public GOComponentPlayer(final GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

	@Override
	public void update(final int dt) {
		// TODO Auto-generated method stub

	}

}
