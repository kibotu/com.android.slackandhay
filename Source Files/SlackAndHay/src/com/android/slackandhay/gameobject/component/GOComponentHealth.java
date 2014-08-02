package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState.StateType;

/**
 * This type of component represents the idea that there is a
 * certain amount of damage a gameObject can take before it will
 * be considered 'dead' or 'destroyed'.
 * 
 * @author til
 *
 */
public abstract class GOComponentHealth extends GOComponent {

	@SuppressWarnings("unused")
	protected final String TAG = GOComponentHealth.class.getSimpleName();
	protected final int fullHealth;
	protected int currentHealth;

	public GOComponentHealth(final int fullHealth, final GOGameObject parent) {
		super(GOComponentType.HEALTH, parent);
		this.fullHealth = fullHealth;
		currentHealth = fullHealth;
	}

	@Override
	public abstract void update(final int dt);
	public abstract void decreaseHealthBy(int damage);
	public abstract int getCurrentHealth();
	public abstract int getHealthPercentage();
	public abstract boolean isAlive();
}
