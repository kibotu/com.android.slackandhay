package com.android.slackandhay.grid;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * @author til
 *
 */
class GridCell {

	@SuppressWarnings("unused")
	private static final String TAG = GridCell.class.getSimpleName();
	private final GridLocation location;
	private GOGameObject occupant;

	public GridCell(final GridLocation location) {
		if (location == null)
			throw new NullPointerException();
		this.location = location;
		occupant = null;
	}

	public GridLocation getLocation() {
		return location;
	}

	public boolean isOccupied() {
		return occupant != null;
	}

	public GOGameObject getOccupant() {
		return occupant;
	}

	public boolean occupy(final GOGameObject occupant)  {
		if (this.occupant != null)
			return false;
		this.occupant = occupant;
		return true;
	}

	public GOGameObject leave() {
		final GOGameObject oldOccupant = occupant;
		occupant = null;
		return oldOccupant;
	}

}
