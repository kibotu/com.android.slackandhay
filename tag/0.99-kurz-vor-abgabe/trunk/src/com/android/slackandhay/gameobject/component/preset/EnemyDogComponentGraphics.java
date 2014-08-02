package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.scene.Animation;

public class EnemyDogComponentGraphics extends GOComponentGraphics{

	public EnemyDogComponentGraphics(final GOGameObject parent) {
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
		case ATTACKING:
		case BLOCKING:
		case RUNNING:
		case WALKING:
			switch(orientation) {
			case SOUTH:
				animate(Animation.DOG_WALKING_SOUTH);
				break;
			case SOUTHEAST:
				animate(Animation.DOG_WALKING_SOUTHEAST);
				break;
			case SOUTHWEST:
				animate(Animation.DOG_WALKING_SOUTHWEST);
				break;
			case EAST:
				animate(Animation.DOG_WALKING_EAST);
				break;
			case WEST:
				animate(Animation.DOG_WALKING_WEST);
				break;
			case NORTH:
				animate(Animation.DOG_WALKING_NORTH);
				break;
			case NORTHEAST:
				animate(Animation.DOG_WALKING_NORTHEAST);
				break;
			case NORTHWEST:
				animate(Animation.DOG_WALKING_NORTHWEST);
				break;
			}
			break;
		case STRUCK:
			animate(Animation.DOG_STRUCK_SOUTH);
			break;
		case DEAD:
			animate(Animation.DOG_DEAD_SOUTH);
			break;
		case DESTROYED:
			animate(Animation.DOG_DEAD_LYING);
			break;
		}
	}
}
