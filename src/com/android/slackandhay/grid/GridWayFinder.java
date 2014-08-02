package com.android.slackandhay.grid;


/**
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 *
 * @deprecated
 */
public class GridWayFinder {

	private final Grid worldGrid;

	public GridWayFinder(final Grid worldGrid) {
		this.worldGrid = worldGrid;
	}

	public GridDirection getNextMove() {
		return GridDirection.NEUTRAL;
	}
}
