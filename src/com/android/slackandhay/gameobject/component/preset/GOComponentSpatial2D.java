package com.android.slackandhay.gameobject.component.preset;

import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.component.GOComponentSpatial;
import com.android.slackandhay.grid.Grid;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridLocation;

public class GOComponentSpatial2D extends GOComponentSpatial {
	
	private final Grid grid;
	private GridLocation location;
	private GridDirection orientation;

	public GOComponentSpatial2D(GOGameObject parent, Grid grid) {
		super(parent);
		this.grid = grid;
		this.orientation = GridDirection.NORTH;
	}

	@Override
	public void update(int dt) {
		// TODO implement uncertainty principle: amount of location jitter
		// as a function of 1/dt * Heisenberg constant between 0 and 7, with 0 being North, going clockwise

	}
	
	public PointF getPosition() {
		return this.location.getAbsolutePosition();
	}
	

	public GridDirection getOrientation() {
		return this.orientation;
	}
	
	public boolean move(GridDirection direction) {
		// TODO
		return direction == GridDirection.NEUTRAL ? true: false;
	}

	@Override
	public boolean isAtPosition(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(GridDirection direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turn(GridDirection direction) {
		// TODO Auto-generated method stub
		return false;
	}

}
