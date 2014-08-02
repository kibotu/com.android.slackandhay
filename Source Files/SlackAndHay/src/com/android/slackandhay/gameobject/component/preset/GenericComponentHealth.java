package com.android.slackandhay.gameobject.component.preset;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.gameobject.component.GOComponentHealth;

public class GenericComponentHealth extends GOComponentHealth{

	public GenericComponentHealth(int fullHealth, GOGameObject parent) {
		super(fullHealth, parent);

	}
	
	@Override
	public void update(final int dt) {
		if(this.currentHealth<=0){
			parent.getStateMananger().proposeState(StateType.DEAD);
		}
	}

	public void decreaseHealthBy(int damage){
		this.currentHealth -= damage;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getHealthPercentage() {
		return currentHealth * 100 / fullHealth;
	}
	public boolean isAlive() {
		return currentHealth > 0;
	}
}
