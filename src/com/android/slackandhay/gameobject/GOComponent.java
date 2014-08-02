package com.android.slackandhay.gameobject;

public abstract class GOComponent {
	public static final String TAG = "GenericComponent";

	public abstract void update(int dt, GOGameObject goGameObject);
}
