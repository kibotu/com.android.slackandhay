package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.gameobject.component.GOComponentDefense;
import com.android.slackandhay.gameobject.component.GOComponentHealth;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;

public class GenericComponentDefense extends GOComponentDefense {
	private static final String TAG = GenericComponentDefense.class.getSimpleName();
	private GOComponentHealth health;
	float blockingDamageDivisor;

	public GenericComponentDefense(GOGameObject parent,	float blockingDamageDivisor) {
		super(parent);
		this.blockingDamageDivisor = blockingDamageDivisor;
		health = (GOComponentHealth) parent.getComponent(GOComponentType.HEALTH);
	}

	public void putDamage(final int damage, final GridDirection direction, final GOGameObject attacker) {
		if(health==null){
			Log.e(TAG, parent.toString()+" has no Health Component!!!");
		} else {
		if(direction==null){
			if (this.parent.getStateMananger().getActiveState().getStateType() == StateType.BLOCKING) {
				this.health.decreaseHealthBy((int) (damage / blockingDamageDivisor));
			} else {
				health.decreaseHealthBy(damage);
			}
		} else {
			//TODO do something smart.
		}
		}
	}

	@Override
	public void update(int dt) {

	}
}
