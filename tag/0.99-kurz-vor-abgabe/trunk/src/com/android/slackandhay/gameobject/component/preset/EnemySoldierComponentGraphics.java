package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.scene.Animation;

public class EnemySoldierComponentGraphics extends GOComponentGraphics{

	public EnemySoldierComponentGraphics(final GOGameObject parent) {
		super(parent);
	}

	@Override
	public void update(final int dt){
		final GOState activeState =  parent.getStateMananger().getActiveState();
		final GOComponentSpatial2D spatial = (GOComponentSpatial2D) parent.getComponent(GOComponentType.SPATIAL);
		assert spatial != null;
		if (!spatial.hasPosition())
			return;
		final float pointx = spatial.getPositionX();
		final float pointy = spatial.getPositionY();
		setAbsPosition(pointx, pointy, -1);
		final GridDirection orientation = spatial.getOrientation();

		switch (activeState.getStateType()) {
		case IDLE:
			animate(Animation.SOLDIER_IDLE_SOUTH);
			break;
		case ATTACKING:
			animate(Animation.SOLDIER_ATTACKING_SOUTH);
			break;
		case BLOCKING:
		case RUNNING:
		case WALKING:
			switch(orientation) {
			case SOUTH:
				animate(Animation.SOLDIER_WALKING_SOUTH);
				break;
			case SOUTHEAST:
				animate(Animation.SOLDIER_WALKING_SOUTHEAST);
				break;
			case SOUTHWEST:
				animate(Animation.SOLDIER_WALKING_SOUTHWEST);
				break;
			case EAST:
				animate(Animation.SOLDIER_WALKING_EAST);
				break;
			case WEST:
				animate(Animation.SOLDIER_WALKING_WEST);
				break;
			case NORTH:
				animate(Animation.SOLDIER_WALKING_NORTH);
				break;
			case NORTHEAST:
				animate(Animation.SOLDIER_WALKING_NORTHEAST);
				break;
			case NORTHWEST:
				animate(Animation.SOLDIER_WALKING_NORTHWEST);
				break;
			}
			break;
		case STRUCK:
			animate(Animation.SOLDIER_STRUCK_SOUTH);
			break;
		case DEAD:
			animate(Animation.SOLDIER_DEAD_SOUTH);
			break;
		case DESTROYED:
			animate(Animation.SOLDIER_DEAD_LYING);
			break;
		}
	}
}
