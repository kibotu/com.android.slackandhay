package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentAI;
import com.android.slackandhay.gameobject.component.GOComponentMovement;
import com.android.slackandhay.gameobject.component.GOComponentOffense;
import com.android.slackandhay.gameobject.component.GOComponentSpatial;
import com.android.slackandhay.gameobject.component.GOComponentType;

public class GenericComponentAI extends GOComponentAI {
	
	private static final float TARGETING_RANGE = 0.5f;

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentAI.class.getSimpleName();

	private GOGameObject target;

	public GenericComponentAI(final GOGameObject parent) {
		super(parent);
	}

	@Override
	public void update(final int dt) {
		if (target == null) {
			return;
		}
		if (targetIsInTargetingRange()) {
			if (targetInStrikeRange()) {
				attackTarget();
			} else {
				approachTarget();
			}
		}
	}

	public void setTarget(final GOGameObject target) {
		this.target = target;
	}

	private boolean targetIsInTargetingRange() {
		boolean returnValue = false;
		GOComponentSpatial spatial = (GOComponentSpatial) parent.getComponent(GOComponentType.SPATIAL);
		if (spatial != null) {
			returnValue = spatial.getDistance(this.target) <= TARGETING_RANGE;
		}
		return returnValue;
	}

	private boolean targetInStrikeRange() {
		boolean returnValue = false;
		GOComponentOffense offense = (GOComponentOffense) parent.getComponent(GOComponentType.OFFENSIVE);
		if (offense != null) {
			returnValue = offense.isInRange(this.target);
		}
		return returnValue;
	}

	private void approachTarget() {
		GOComponentMovement movement = (GOComponentMovement) parent.getComponent(GOComponentType.MOVEMENT);
		if (movement != null && movement instanceof GOComponentFollowMovement) {
			((GOComponentFollowMovement)movement).setTargetSpatial(this.target); 
		}
	}

	private void attackTarget() {
		parent.getStateMananger().proposeState(GOState.StateType.ATTACKING);
	}

}
