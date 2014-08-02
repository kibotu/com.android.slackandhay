package com.android.slackandhay;

import com.android.slackandhay.gameobject.GOGameObject;

import java.util.Vector;

/**
 * This class provides synchronization between the game thread and the render
 * thread
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class RenderSychronizer {

	@SuppressWarnings("unused")
	private static final String TAG = RenderSychronizer.class.getSimpleName();
	private final Vector<GOGameObject> _gameObjects;
	private static RenderSychronizer INSTANCE = new RenderSychronizer();

	private RenderSychronizer() {
		_gameObjects = new Vector<GOGameObject>();
	}

	/**
	 * <code>synchronized</code>
	 * 
	 * @return
	 */
	public static synchronized RenderSychronizer getInstance() {
		return INSTANCE;
	}

	public Vector<GOGameObject> getGOGameObjects() {
		return _gameObjects;
	}

	public void reset() {
		INSTANCE = new RenderSychronizer();
	}

}