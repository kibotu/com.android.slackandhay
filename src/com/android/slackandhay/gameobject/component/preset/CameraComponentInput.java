package com.android.slackandhay.gameobject.component.preset;

import android.graphics.Point;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.component.GOComponentInput;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.input.InputSystem;

public class CameraComponentInput  extends GOComponentInput{

	Point lastLeftInput = new Point(0,0);

	public CameraComponentInput(final GOGameObject parent) {
		super(parent);

	}

	@Override
	public void update(final int dt) {
		lastLeftInput = InputSystem.getInstance().getLeftTouchOffset();
		//final GOComponentSpatial2D spa = (GOComponentSpatial2D) parent.getComponent(GOComponentType.SPATIAL);
		//spa.move(GridDirection.calculateDirection(lastLeftInput.x, lastLeftInput.y));

		if (Math.abs(lastLeftInput.y) > Math.abs(lastLeftInput.x)) {
			if (lastLeftInput.y < 0) {
				((GOComponentSpatial2D) parent
						.getComponent(GOComponentType.SPATIAL))
						.move(GridDirection.SOUTH);

			}
			if (lastLeftInput.y > 0) {
				((GOComponentSpatial2D) parent
						.getComponent(GOComponentType.SPATIAL))
						.move(GridDirection.NORTH);
			}
		} else {
			if (lastLeftInput.x > 0) {
				((GOComponentSpatial2D) parent
						.getComponent(GOComponentType.SPATIAL))
						.move(GridDirection.WEST);
			}
			if (lastLeftInput.x < 0) {
				((GOComponentSpatial2D) parent
						.getComponent(GOComponentType.SPATIAL))
						.move(GridDirection.EAST);
			}
		}
	}
}
