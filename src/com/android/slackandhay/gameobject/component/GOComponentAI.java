package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.component.GOComponent;

public class GOComponentAI extends GOComponentControl {
	
	private GOGameObject target;

	public GOComponentAI(GOGameObject parent) {
		super(parent);
	}

	@Override
	public void update(int dt) {
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
	
	private void setTarget(GOGameObject target) {
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
