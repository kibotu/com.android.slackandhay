package com.android.slackandhay.gameobject.component;

import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridLocation;

public class GOComponentSpatial2D extends GOComponent {
	
	private GridLocation location;
	private GridDirection orientation;

	public GOComponentSpatial2D(GOGameObject parent) {
		super(GOComponent.ComponentType.SPATIAL, parent);
		this.orientation = GridDirection.NORTH;
	}

	@Override
	public void update(int dt) {
		// TODO implement uncertainty principle: amount of location jitter
		// as a function of 1/dt * Heisenberg constant 

	}
	
	public boolean setLocation(int x, int y) {
		// TODO
		return false;
	}
	
	public GridLocation getLocation() {
		return this.location;
	}
	
	public GridLocation getNeighbor(GridDirection direction) {
		// TODO
		return null;
	}
	
	public PointF getAbsolutePosition() {
		return this.location.getAbsolutePosition();
	}
	
	/**
	 * @return an integer between 0 and 7, with 0 being North, going clockwise
	 */
	public int getOrientation() {
		return this.orientation.ordinal() + 1;
	}
	
	public boolean move(GridDirection direction) {
		return direction == GridDirection.NEUTRAL ? true: false;
	}

}
