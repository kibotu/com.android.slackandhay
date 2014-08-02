package com.android.slackandhay;

import com.android.slackandhay.gameobject.GOGameObject;

import java.util.Vector;

public class Model {
	private Vector<GOGameObject> _gameObjects;
	private final static Model INSTANCE = new Model(); 
	
	private Model () {
		_gameObjects = new Vector<GOGameObject>();
	}
	
	public static Model getInstance() {
		return INSTANCE;
	}
	
	public Vector<GOGameObject> getGOGameObjects() {
		return _gameObjects;
	}
}