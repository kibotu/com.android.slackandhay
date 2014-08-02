package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;

public class GOComponentHealth extends GOComponent {
	
	private final String TAG = "GenericHealthComponent";
	private final int fullHealth;
	private int currentHealth;

	public GOComponentHealth(int fullHealth, GOGameObject parent) {
		super(GOComponentType.HEALTH, parent);
		this.fullHealth = fullHealth;
		this.currentHealth = fullHealth;
	}

	@Override
	public void update(int dt) {
		// TODO Auto-generated method stub

	}
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	public int getHealthPercentage() {
		return (this.currentHealth * 100) / this.fullHealth;
	}
	public boolean isAlive() {
		return this.currentHealth > 0;
	}

}
