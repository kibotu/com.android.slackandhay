package com.android.slackandhay.gameobject;

import android.util.Log;

/**
 * This class implements the state manager which handles the state of a game
 * object
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public abstract class GOStateManager {

	@SuppressWarnings("unused")
	private static final String TAG = GOStateManager.class.getSimpleName();
	protected int activeState = 0;
	protected int lastState = 0;
	protected final int NUMBER_OF_STATES = 0;
	protected GOState[] stateTable;

	/*
	 * THIS COMMENT SHALL STAY HERE SO THAT I DON'T HAVE TO REMEMBER THE
	 * STRUCURE! public GOStateManager(){ //The idle duration has to be long, so
	 * that it can be easily animated... stateTable[0] = new
	 * GOState(GOState.StateType.IDLE, 0, 10000); stateTable[1] = new
	 * GOState(GOState.StateType.WALKING, 0, 100); stateTable[2] = new
	 * GOState(GOState.StateType.RUNNING, 0, 100); stateTable[3] = new
	 * GOState(GOState.StateType.ATTACKING, 0, 100); stateTable[4] = new
	 * GOState(GOState.StateType.BLOCKING, 0, 100); stateTable[5] = new
	 * GOState(GOState.StateType.STRUCK, 0, 100); stateTable[6] = new
	 * GOState(GOState.StateType.DEAD, 7, 100); stateTable[7] = new
	 * GOState(GOState.StateType.DESTROYED, 7, 100);
	 * 
	 * stateTable[activeState].start(0); }
	 */

	/**
	 * Has to be implemented inside the {@link GOGameObjectFactory}
	 */
	protected GOStateManager() {
		verify();
	}

	private void verify() {
		int i = 0;
		// TODO
		// for()
	}

	/**
	 * the update method lets states perform their default transitions and also
	 * performs the carry over of time once a state is left
	 * 
	 * @param dt
	 *            the time that this state manager shall progress in
	 *            milliseconds
	 */
	public void update(final int dt) {
		int timeLeft = dt;
		try {
			stateTable[activeState].update(dt);
			// perform the default transition until all the time left is used
			// up.
			while (stateTable[activeState].hasExpiredSince() > 0 &&
			// only perform state transition if the next state would be
			// different from the current state
					stateTable[activeState].getStateType() != stateTable[stateTable[activeState]
							.getDefaultTransitionIndex()].getStateType()) {
				timeLeft -= stateTable[activeState].hasExpiredSince();
				activeState = stateTable[activeState].getDefaultTransitionIndex();
				stateTable[activeState].start(timeLeft);
				// Log.e(TAG,
				// "Default Transistion: "+stateTable[activeState].getStateType());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "WRONG STATE TABLE!");
		}
		lastState = activeState;
	}

	/**
	 * this method allow to propose a state. the state is accepted if it has a
	 * higher priority (meaning higher index in the state table) than the
	 * current state
	 * 
	 * @param proposal
	 *            the proposed state
	 * @return true if the state was accepted, false if not.
	 */
	public boolean proposeState(final GOState.StateType proposal) {
		// Log.e(TAG, "current State: "+stateTable[activeState].getStateType());
		// Log.e(TAG,
		// "current State expire: "+stateTable[activeState].hasExpiredSince());
		// Log.e(TAG, "State was proposed: "+proposal);
		int proposalIndex = 0;
		// proposal to idle can be skipped (i=1)
		for (int i = 1; proposalIndex < 1 || i < NUMBER_OF_STATES; i++) {
			if (stateTable[i].getStateType() == proposal) {
				proposalIndex = i;
			}
		}
		if (proposalIndex > activeState) {
			lastState = activeState;
			activeState = proposalIndex;
			stateTable[activeState].start(0);
			// Log.e(TAG,
			// "Accepted State: "+stateTable[activeState].getStateType());
			return true;
		}
		// Log.e(TAG, "proposal denied");
		return false;
	}

	/**
	 * returns the active state
	 * 
	 * @return the active state
	 */
	public GOState getActiveState() {
		return stateTable[activeState];
	}

	/**
	 * returns the last active state
	 * 
	 * @return the last active state
	 */
	public GOState getLastState() {
		return stateTable[lastState];
	}

	/**
	 * resets the state manager in terms of setting the current state to the
	 * first state, which is probably the IDLE state
	 */
	public void reset() {
		// IDLE is always state number zero
		lastState = activeState;
		activeState = 0;
		stateTable[activeState].start(0);
	}

}
