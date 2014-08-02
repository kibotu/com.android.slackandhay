package com.android.slackandhay.grid;


public class GridWayFinder {

	private final Grid worldGrid;

	public GridWayFinder(final Grid worldGrid) {
		this.worldGrid = worldGrid;
	}

	public GridDirection getNextMove() {
		return GridDirection.NEUTRAL;
	}
}
