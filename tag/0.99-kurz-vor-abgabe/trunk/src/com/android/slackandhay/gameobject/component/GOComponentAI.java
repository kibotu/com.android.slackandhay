package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public abstract class GOComponentAI extends GOComponentControl {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentAI.class.getSimpleName();

	private GOGameObject target;

	public GOComponentAI(final GOGameObject parent) {
		super(parent);
	}

	@Override
	public void update(final int dt) {
		// TODO
		/*		if (!target.health.isAlive()) {
			this.target = null;
		}
		if (this.target == null) {
			this.target = acquireTarget();
		}*/
		if (!targetInRange()) {
			approachTarget();
		} else {
			attackTarget();
		}

	}

	private void setTarget(final GOGameObject target) {
		this.target = target;
	}

	private GOGameObject acquireTarget() {
		// return this.faction.getLiveTarget()
		return null;
	}

	private boolean targetInRange() {
		// return this.parent.offense.locationIsInRange(target.location)
		return false;
	}

	private void approachTarget() {
	}

	private void attackTarget() {
		// this.parent.stateManager.proposeState(ATTACKING)
	}

}
