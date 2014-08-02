package com.android.slackandhay;

import java.util.Vector;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 *<code>Syncronized</code> by internal <code>Vector</code> wrappers.
 */
public class RenderSychronizer {

	@SuppressWarnings("unused")
	private static final String TAG = RenderSychronizer.class.getSimpleName();
	private final Vector<GOGameObject> _gameObjects;
	private static RenderSychronizer INSTANCE = new RenderSychronizer();

	private RenderSychronizer () {
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