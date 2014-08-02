package com.android.slackandhay.gameobject;

public class GOState {
	public enum StateType {
		IDLE, WALKING, RUNNING, ATTACKING, BLOCKING, STRUCK, DEAD, DESTROYED
	};

	private StateType stateType = StateType.IDLE;
	private int stateCurrentTime = 0;
	private final int stateDuration;
	private final int defaultTransitionIndex;

	/**
	 * Creates a new State.
	 * 
	 * @param stateType
	 *            has to be one of the provided stateIDs, e.g.
	 *            GOState.StateID.IDLE
	 * @param duration
	 *            specifies the duration of this very state.
	 */
	public GOState(StateType stateType, int defaultTransitionIndex, int duration) {
		this.stateType = stateType;
		this.stateDuration = duration;
		this.defaultTransitionIndex = defaultTransitionIndex;
	}

	/**
	 * Starts a State.
	 * 
	 * @param startOffsetTime
	 *            Sets the currentTime to the desired offset. Normally you can
	 *            just pass 0 as argument to start the state at its beginning.
	 */
	public void start(int startOffsetTime) {
		// duration of the state is reset, so that it can be incremented on
		// every update.
		stateCurrentTime = 0;
	}

	/**
	 * updates a State.
	 * 
	 * @param dt
	 *            increments the state's currentTime by dt.
	 */
	public void update(int dt) {
		stateCurrentTime += dt;
	}

	/**
	 * Determines whether a state has expired
	 * 
	 * @return either a negative number: meaning the state has not expired, but
	 *         will in n milliseconds
	 *         or a positive number: which tells you for
	 *         how many milliseconds the state has expired.
	 * 
	 */
	public int hasExpiredSince() {
		return stateCurrentTime - stateDuration;
	}

	public int getCurrentTime(){
		return this.stateCurrentTime;
	}
	public int getDuration(){
		return this.stateDuration;
	}
	
	/**
	 * Returns the StateID.
	 * 
	 * @return The stateID
	 */
	public StateType getStateType() {
		return stateType;
	}

	public int getDefaultTransitionIndex() {
		return defaultTransitionIndex;
	}
}
