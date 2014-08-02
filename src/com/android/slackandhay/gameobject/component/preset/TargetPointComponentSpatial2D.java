package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridWorld;

public class TargetPointComponentSpatial2D  extends GOComponentSpatial2D {

	private static final String TAG = TargetPointComponentSpatial2D.class.getSimpleName();

	public TargetPointComponentSpatial2D(final GOGameObject parent, final GridWorld grid) {
		super(parent, grid);
	}

	@Override
	public boolean canMove(final GridDirection direction) {
		final int newid = grid.transformIDbyDirection(currentLocationID, direction);
		if (newid == currentLocationID)
			return false;
		return true;
	}

	@Override
	public boolean move(final GridDirection direction) {
		final int newid = grid.transformIDbyDirection(currentLocationID, direction);
		if (newid == currentLocationID)
			return false;
		put(newid);
		return true;
	}


	@Override
	protected boolean put(final int locationID) {
		if (!grid.isValidID(locationID)) {
			Log.w(TAG, "put() called with invalid id: " + locationID);
			return false;
		}
		currentLocationID = locationID;
		updateCurrentLocationPoint();
		return true;
	}

}
