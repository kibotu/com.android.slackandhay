package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.scene.Animation;

public class DecorationComponentGraphics extends GOComponentGraphics {

	private Animation animation;
	
	public DecorationComponentGraphics(final GOGameObject parent, Animation animation) {
		super(parent);
		this.animation = animation;
	}
	
	@Override
	public void update(int dt) {
		final GOState activeState =  parent.getStateMananger().getActiveState();
		final GOComponentSpatial2D spatial = (GOComponentSpatial2D) parent.getComponent(GOComponentType.SPATIAL);
		assert spatial != null;
		if (!spatial.hasPosition())
			return;
		final float pointx = spatial.getPositionX();
		final float pointy = spatial.getPositionY();
		setAbsPosition(pointx, pointy, -1);
//		final GridDirection orientation = spatial.getOrientation();

		switch (activeState.getStateType()) {
		case IDLE:
			animate(animation);
		}
	}
}
