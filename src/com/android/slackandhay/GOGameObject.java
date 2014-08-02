package com.android.slackandhay;

public abstract class GOGameObject {
	private int UID = SlackAndHayMain.getNewUID();
	protected GOComponent[] components;
	public GOStateManager stateManager;

	/**
	 * Updates all components which have been initialized in the subclasses of
	 * GOGameObject.
	 * 
	 * @param dt
	 *            specifies the amount of time, which has passed since the last
	 *            update.
	 */
	protected void update(int dt) {
		final int numberofcomponents = components.length;
		for (int i = 0; i < numberofcomponents; i++) {
			components[i].update(dt, this);
		}
	}

	public int getUID() {
		return UID;
	}
}
