package com.android.slackandhay.grid;



import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;

public class GridWorld extends Grid<GOGameObject> {

	public GridWorld(final int width, final int height, final PointF origin, final float scale) {
		super(width, height, origin, scale);
	}


	public void putDamage(final int id) {
		// TODO
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
