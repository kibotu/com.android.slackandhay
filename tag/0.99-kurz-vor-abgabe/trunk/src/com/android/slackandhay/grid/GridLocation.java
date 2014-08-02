package com.android.slackandhay.grid;

import android.graphics.PointF;



/**
 * @deprecated	an Integer id is used instead
 * @author til
 *
 */
@Deprecated
public class GridLocation {

	final int x;
	final int y;

	private final PointF geoPosition;

	GridLocation(final int x, final int y, final PointF worldPosition) {
		this.x = x;
		this.y = y;
		//		final int xOffset = (x - GRID_LOCATION_OF_ORIGIN.x) * PIXELS_PER_GRID_LOCATION;
		//		final int yOffset = (y - GRID_LOCATION_OF_ORIGIN.y) * PIXELS_PER_GRID_LOCATION;
		geoPosition = worldPosition;
	}

	public PointF getAbsolutePosition() {
		return geoPosition;
	}

}
