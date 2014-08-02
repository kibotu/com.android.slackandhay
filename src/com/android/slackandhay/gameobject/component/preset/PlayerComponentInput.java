package com.android.slackandhay.gameobject.component.preset;

import android.graphics.Point;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentInput;
import com.android.slackandhay.gameobject.component.GOComponentMovement;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.input.InputSystem;

public class PlayerComponentInput extends GOComponentInput {

	@SuppressWarnings("unused")
	private static final String TAG = PlayerComponentInput.class.getSimpleName();

	Point lastLeftInput = new Point(0,0);

	public PlayerComponentInput(final GOGameObject parent) {
		super(parent);

	}

	@Override
	public void update(final int dt) {
		if (InputSystem.getInstance().buttonWasPressed(0)) {
			//			Log.d(TAG, "button 1 pressed, proposing attack");
			parent.getStateMananger().proposeState(GOState.StateType.ATTACKING);
		}
		if(InputSystem.getInstance().buttonWasPressed(1)){
			//			Log.d(TAG, "button 1 pressed, proposing walk");
			parent.getStateMananger().proposeState(GOState.StateType.RUNNING);
		}

		if(InputSystem.getInstance().buttonWasPressed(2)){
			parent.getStateMananger().proposeState(GOState.StateType.BLOCKING);
			final GOComponentMovement move = (GOComponentMovement) parent.getComponent(GOComponentType.MOVEMENT);
			move.setTarget(0.2f, 0.4f);
			//			Log.d(TAG, "button 1 pressed, proposing block");
		}

		lastLeftInput = InputSystem.getInstance().getLeftTouchOffset();

		//TODO Remove casts later (when components are saved as instance variables)

		//GOComponentSpatial2D spa = (GOComponentSpatial2D) parent.getComponent(GOComponentType.SPATIAL);


		/*if (Math.abs(lastLeftInput.y) > Math.abs(lastLeftInput.x)) {
			if (lastLeftInput.y > 0) {
				((GOComponentSpatial2D) parent
						.getComponent(GOComponentType.SPATIAL))
						.move(GridDirection.SOUTH);
			}
			if (lastLeftInput.y < 0) {
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
		}*/
		// TODO Remove casts later (when components are saved as instance
		// variables)

		// GOComponentSpatial2D spa = (GOComponentSpatial2D)
		// parent.getComponent(GOComponentType.SPATIAL);

		// spa.move(GridDirection.calculateDirection(lastLeftInput.x,
		// lastLeftInput.y));
	}

}
