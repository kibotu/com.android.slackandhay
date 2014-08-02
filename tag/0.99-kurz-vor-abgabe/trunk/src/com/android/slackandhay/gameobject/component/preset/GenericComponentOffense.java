package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponent;
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
			GOComponentSpatial2D spatial = ((GOComponentSpatial2D)parent.getComponent(GOComponentType.SPATIAL));
			int i = grid.transformIDbyDirection(spatial.getPositionID(),spatial.getOrientation());
			if (i == spatial.getPositionID()) {
				return;
			}
			//Log.d(TAG, "Attacking GO at grid id:"+i);
			Log.d(TAG, parent.toString()+" has orentation: "+spatial.getOrientation());
			if(grid.get(i)!=null){
				GenericComponentDefense def = ((GenericComponentDefense)grid.get(i).getComponent(GOComponentType.DEFENSIVE));
				if(def!=null){
					def.putDamage(this.hitpoints, null, parent);
				} else {
					Log.e(TAG, grid.get(i).toString()+" has no defense component!!");
				}
			}
		}
		if(parent.getStateMananger().getLastState().getStateType() == GOState.StateType.ATTACKING && 
				parent.getStateMananger().getActiveState().getStateType() != GOState.StateType.ATTACKING){
			this.lastState = parent.getStateMananger().getActiveState().getStateType();
		}
	}

}
