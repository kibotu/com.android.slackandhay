package com.android.slackandhay;

public class GOState {
	public enum StateID {
		IDLE, WALK, RUN, ATTACK, BLOCK
	};

	private StateID stateID = StateID.IDLE;
	private int stateCurrentTime = 0;
	private final int stateDuration;

	/**
	 * Creates a new State.
	 * 
	 * @param stateID
	 *            has to be one of the provided stateIDs, e.g.
	 *            GOState.StateID.IDLE
	 * @param duration
	 *            specifies the duration of this very state.
	 */
	public GOState(StateID stateID, int duration) {
		this.stateID = stateID;
		this.stateDuration = duration;
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
		return stateDuration - stateCurrentTime;
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
	public StateID getStateID() {
		return stateID;
	}
}
