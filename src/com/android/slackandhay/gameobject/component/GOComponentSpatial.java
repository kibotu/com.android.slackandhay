package com.android.slackandhay.gameobject.component;

import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;

public abstract class GOComponentSpatial extends GOComponent {

	public GOComponentSpatial(GOGameObject parent) {
		super(GOComponentType.SPATIAL, parent);
		// TODO Auto-generated constructor stub
	}

	public GOComponentSpatial(GOComponentType type, GOGameObject parent,
			GOComponentType[] requiredComponents) {
		super(type, parent, requiredComponents);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int dt) {
		// TODO Auto-generated method stub

	}
	
	public abstract boolean isAtPosition(float x, float y);
	public abstract PointF getPosition();
	public abstract GridDirection getOrientation();
	public abstract boolean canMove(GridDirection direction);
	public abstract boolean move(GridDirection direction);
	public abstract boolean turn(GridDirection direction);
	

}
