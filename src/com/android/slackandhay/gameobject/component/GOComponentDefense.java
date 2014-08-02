package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;

public class GOComponentDefense extends GOComponent {
	
	private static final String TAG = "GenericDefensiveComponent";

	public GOComponentDefense(GOComponentType type, GOGameObject parent) {
		super(type, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int dt) {
		// TODO Auto-generated method stub

	}
	
	public void takeDamage(int damage, GridDirection direction) {
/*		if (this.parent.stateManager.state == BLOCKING) {
			return;
		}
		this.parent.health.decreaseBy(damage);
*/	}

}
