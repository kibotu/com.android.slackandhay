package com.android.slackandhay.gameobject;


/**
 * This enum represents a state inside the state manager
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 *
 */
public class GOState {
	public enum StateType {
		IDLE, WALKING, RUNNING, ATTACKING, BLOCKING, STRUCK, DEAD, DESTROYED
	};

	private StateType stateType = StateType.IDLE;
	private int stateCurrentTime = 0;
	private final int stateDuration;
	private final int defaultTransitionIndex;
	private int counter = 0;

	/**
	 * Creates a new State.
	 * 
	 * @param stateType
	 *            has to be one of the provided stateIDs, e.g.
	 *            GOState.StateID.IDLE
	 * @param duration
	 *            specifies the duration of this very state.
	 */
	public GOState(final StateType stateType, final int defaultTransitionIndex, final int duration) {
		this.stateType = stateType;
		stateDuration = duration;
		this.defaultTransitionIndex = defaultTransitionIndex;
	}

	/**
	 * Starts a State.
	 * 
	 * @param startOffsetTime
	 *            Sets the currentTime to the desired offset. Normally you can
	 *            just pass 0 as argument to start the state at its beginning.
	 */
	public void start(final int startOffsetTime) {
		// duration of the state is reset, so that it can be incremented on
		// every update.
		stateCurrentTime = 0;
		counter++;
	}

	/**
	 * updates a State.
	 * 
	 * @param dt
	 *            increments the state's currentTime by dt.
	 */
	public void update(final int dt) {
		stateCurrentTime += dt;
	}

	/**
	 * Calculates the percentage the transition between states
	 * @return
	 * a float between 0 and 1 ... except the state is overdue, then it could be more.
	 */
	public float getPercentage(){
		return (float)stateCurrentTime / (float)stateDuration;
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

	public int getCurrentTime() {
		return stateCurrentTime;
	}

	public int getDuration() {
		return stateDuration;
	}

	/**
	 * Returns the StateID.
	 * 
	 * @return The stateID
	 */
	public StateType getStateType() {
		return stateType;
	}

	/**
	 * returns the default transition index
	 * @return
	 * 		the default transition index
	 */
	public int getDefaultTransitionIndex() {
		return defaultTransitionIndex;
	}

	/**
	 * Returns how many times the state was active. This will help implementing
	 * everything that repeatedly uses the same state, because each state now
	 * can tell for how long it was running.
	 * 
	 * @return times the state was active
	 */
	public int getCounter() {
		return counter;
	}
}
