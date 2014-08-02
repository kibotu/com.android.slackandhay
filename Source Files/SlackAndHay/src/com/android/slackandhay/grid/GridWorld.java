package com.android.slackandhay.grid;



import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;

/**
 * this class contains the grid in which all game objects operate
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class GridWorld extends Grid<GOGameObject> {

	public GridWorld(final int width, final int height, final PointF origin, final float scale) {
		super(width, height, origin, scale);
	}

	@Override
	protected GOGameObject[] initialize() {
		final GOGameObject[] cells = new GOGameObject[width * height];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = null;
		}
		return cells;
	}





}
