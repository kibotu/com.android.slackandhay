package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentOffense;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridWorld;

public class GenericComponentOffense extends GOComponentOffense {

	
	private static final String TAG = GenericComponentOffense.class.getSimpleName();
	private GridWorld grid;
	private int hitpoints;
	private GOState.StateType lastState = GOState.StateType.IDLE;
	
	public GenericComponentOffense(GOGameObject parent, GridWorld grid, int hitpoints ) {
		super(parent);
		this.grid = grid;
		this.hitpoints = hitpoints;
	}

	@Override
	public void update(int dt) {
		//if(parent.getStateMananger().getActiveState().getStateType() == GOState.StateType.ATTACKING && lastState != GOState.StateType.ATTACKING){
		if(parent.getStateMananger().getActiveState().getStateType() == GOState.StateType.ATTACKING){
			lastState = GOState.StateType.ATTACKING;
			GOGameObject targeted = getTargetedObject();
			if(targeted !=null && targeted != this.parent){
				GenericComponentDefense def = ((GenericComponentDefense)targeted.getComponent(GOComponentType.DEFENSIVE));
				if(def!=null){
					def.putDamage(this.hitpoints, null, parent);
				} else {
					Log.e(TAG, targeted.toString()+" has no defense component!!");
				}
			}
		}
		if(parent.getStateMananger().getLastState().getStateType() == GOState.StateType.ATTACKING && 
				parent.getStateMananger().getActiveState().getStateType() != GOState.StateType.ATTACKING){
			this.lastState = parent.getStateMananger().getActiveState().getStateType();
		}
	}
	
	@Override
	public boolean isInRange(GOGameObject target) {
		if (target == null || target == this.parent) {
			return false;
		}
		return target.equals(getTargetedObject());
	}
	
	private GOGameObject getTargetedObject() {
		GOComponentSpatial2D spatial = ((GOComponentSpatial2D)parent.getComponent(GOComponentType.SPATIAL));
		int i = grid.transformIDbyDirection(spatial.getPositionID(),spatial.getOrientation());
		return grid.get(i); 
	}

}
