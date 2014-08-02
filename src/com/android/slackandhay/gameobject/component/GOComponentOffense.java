package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridLocation;

public class GOComponentOffense extends GOComponent {
	
	private static final String TAG = "GenericOffensiveComponent";
	
	protected GridLocation target;

	public GOComponentOffense(GOComponentType type, GOGameObject parent) {
		super(type, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int dt) {
		// TODO Auto-generated method stub

	}
	
	public boolean locationIsInRange(GridLocation location) {
		//TODO
		return true;
	}
	
	public boolean attack() {
		//TODO
		return false;
	}
	

}
