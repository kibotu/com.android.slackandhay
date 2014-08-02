package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.component.GOComponentMovement;
import com.android.slackandhay.gameobject.component.GOComponentSpatial;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridWorld;

public class GOComponentFollowMovement extends GOComponentMovement {

	private final GOComponentSpatial spatial;
	private GOComponentSpatial targetSpatial = null;

	public GOComponentFollowMovement(final GOGameObject parent, final GridWorld grid) {
		super(parent, grid);
		spatial = (GOComponentSpatial) parent.getComponent(GOComponentType.SPATIAL);
	}

	public void setTargetSpatial(final GOGameObject target) {
		targetSpatial = (GOComponentSpatial) target
		.getComponent(GOComponentType.SPATIAL);
	}

	private GridDirection direction;

	@Override
	public void update(final int dt) {
		if (targetSpatial != null) {
			final int selfPosID = world.pointToID(spatial.getPositionX(), spatial.getPositionY());
			final int targetPosID = world.pointToID(targetSpatial.getPositionX(),	targetSpatial.getPositionY());
			direction = world.calculateBestDirection(selfPosID, targetPosID);
			spatial.move(direction);
		}
	}

}
