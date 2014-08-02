package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public abstract class GOComponentControl extends GOComponent {
	
	private final String TAG = "GenericControlComponent";

	public GOComponentControl(GOGameObject parent) {
		super(GOComponentType.CONTROL, parent);
	}

}
