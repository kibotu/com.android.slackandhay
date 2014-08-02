package com.android.slackandhay.grid;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * @author til
 *
 */
class GridCell {

	private final GridLocation location; 
	private GOGameObject occupant;

	public GridCell(GridLocation location) {
		if (location == null) {
			throw new NullPointerException();
		}
		this.location = location;
		this.occupant = null;
	}
	
	public GridLocation getLocation() {
		return location;
	}

	public boolean isOccupied() {
		return this.occupant != null;
	}
	
	public GOGameObject getOccupant() {
		return this.occupant;
	}
	
	public boolean occupy(GOGameObject occupant)  {
		if (this.occupant != null) {
			return false;
		}
		this.occupant = occupant;
		return true;
	}
	
	public GOGameObject leave() {
		GOGameObject oldOccupant = this.occupant;
		this.occupant = null;
		return oldOccupant;
	}

}
