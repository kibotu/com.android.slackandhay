package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.scene.Animation;

public class PlayerComponentGraphics extends GOComponentGraphics {

	public PlayerComponentGraphics(final GOGameObject parent) {
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
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				animate(Animation.CRONO_IDLE_SOUTH);
				break;
			case EAST:
				animate(Animation.CRONO_IDLE_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_IDLE_WEST);
				break;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				animate(Animation.CRONO_IDLE_NORTH);
				break;
			}
			break;
		case WALKING:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				animate(Animation.CRONO_WALKING_SOUTH);
				break;
			case EAST:
				animate(Animation.CRONO_WALKING_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_WALKING_WEST);
				break;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				animate(Animation.CRONO_WALKING_NORTH);
				break;
			}
			break;
		case RUNNING:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				animate(Animation.CRONO_RUNNING_SOUTH);
				break;
			case EAST:
				animate(Animation.CRONO_RUNNING_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_RUNNING_WEST);
				break;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				animate(Animation.CRONO_RUNNING_NORTH);
				break;
			}
			break;
		case ATTACKING:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
				animate(Animation.CRONO_ATTACKING_SOUTHEAST);
				break;
			case SOUTHWEST:
				animate(Animation.CRONO_ATTACKING_SOUTHWEST);
				break;
			case EAST:
				animate(Animation.CRONO_ATTACKING_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_ATTACKING_WEST);
				break;
			case NORTH:
			case NORTHEAST:
				animate(Animation.CRONO_ATTACKING_NORTHEAST);
				break;
			case NORTHWEST:
				animate(Animation.CRONO_ATTACKING_NORTHWEST);
				break;
			}
			break;
		case BLOCKING:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
				animate(Animation.CRONO_BLOCKING_SOUTHEAST);
				break;
			case SOUTHWEST:
				animate(Animation.CRONO_BLOCKING_SOUTHWEST);
				break;
			case EAST:
				animate(Animation.CRONO_BLOCKING_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_BLOCKING_WEST);
				break;
			case NORTH:
			case NORTHEAST:
				animate(Animation.CRONO_BLOCKNIG_NORTHEAST);
				break;
			case NORTHWEST:
				animate(Animation.CRONO_BLOCKNIG_NORTHWEST);
				break;
			}
			break;
		case STRUCK:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
				animate(Animation.CRONO_STRUCK_SOUTHEAST);
				break;
			case SOUTHWEST:
				animate(Animation.CRONO_STRUCK_SOUTHWEST);
				break;
			case EAST:
				animate(Animation.CRONO_STRUCK_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_STRUCK_WEST);
				break;
			case NORTH:
			case NORTHEAST:
				animate(Animation.CRONO_STRUCK_NORTHEAST);
				break;
			case NORTHWEST:
				animate(Animation.CRONO_STRUCK_NORTHWEST);
				break;
			}
			break;
		case DEAD:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				animate(Animation.CRONO_DEAD_SOUTH);
				break;
			case EAST:
				animate(Animation.CRONO_DEAD_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_DEAD_WEST);
				break;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				animate(Animation.CRONO_DEAD_NORTH);
				break;
			}
			break;
		case DESTROYED:
			switch(orientation) {
			case SOUTH:
			case SOUTHEAST:
			case SOUTHWEST:
				animate(Animation.CRONO_DESTROYED_SOUTH);
				break;
			case EAST:
				animate(Animation.CRONO_DESTROYED_EAST);
				break;
			case WEST:
				animate(Animation.CRONO_DESTROYED_WEST);
				break;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				animate(Animation.CRONO_DESTROYED_NORTH);
				break;
			}
			break;
		}
	}
}
