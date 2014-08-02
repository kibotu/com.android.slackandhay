package com.android.slackandhay.grid;

import android.graphics.PointF;



public class GridLocation {
	
	final int x;
	final int y;
	
	private final PointF geoPosition; 
	
	GridLocation(int x, int y, PointF worldPosition) {
		this.x = x;
		this.y = y;
//		final int xOffset = (x - GRID_LOCATION_OF_ORIGIN.x) * PIXELS_PER_GRID_LOCATION;
//		final int yOffset = (y - GRID_LOCATION_OF_ORIGIN.y) * PIXELS_PER_GRID_LOCATION;
		this.geoPosition = worldPosition;
	}
	
	public PointF getAbsolutePosition() {
		return this.geoPosition;
	}
	
}
