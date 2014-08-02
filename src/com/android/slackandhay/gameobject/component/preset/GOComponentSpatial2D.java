package com.android.slackandhay.gameobject.component.preset;

import android.util.Log;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponentSpatial;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridWorld;

public class GOComponentSpatial2D extends GOComponentSpatial {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentSpatial2D.class.getSimpleName();

	public static final GridDirection DEFAULT_ORIENTATION = GridDirection.SOUTH;

	protected static final int INVALID_POSITION_ID = -1;
	protected static final float INVALID_POSITION_FLOAT = Float.POSITIVE_INFINITY;

	protected int currentLocationID = INVALID_POSITION_ID;
	private float currentLocationX = INVALID_POSITION_FLOAT;
	private float currentLocationY = INVALID_POSITION_FLOAT;
	private GridDirection orientation;
	private final MovementSimulator movementSimulator;

	public GOComponentSpatial2D(final GOGameObject parent, final GridWorld grid) {
		super(parent, grid);
		orientation = DEFAULT_ORIENTATION;
		movementSimulator = new MovementSimulator(this);
	}

	@Override
	public void update(final int dt) {
		final GOState state = parent.getStateMananger().getActiveState();
		if (movementSimulator.isActive()) {
			if (state.getStateType().equals(GOState.StateType.WALKING) ||
					state.getStateType().equals(GOState.StateType.RUNNING))
			{
				movementSimulator.update(state.getPercentage());
			} else {
				movementSimulator.stop();
			}
		}
		//TODO
		//TODO DIRTY HACK FOR Z ORDER!!!
		//TODO
		//((GOComponentGraphics)parent.getComponent(GOComponentType.GRAPHICS)).setZPosition(-1f+this.getPositionY());
		//------------------------------
	}

	@Override
	public boolean hasPosition() {
		return grid.isValidID(currentLocationID);
	}

	@Override
	public boolean isAtPosition(final float xf, final float yf) {
		return grid.pointToID(xf, yf) == currentLocationID;
	}

	@Override
	public float getPositionX() {
		return currentLocationX;
	}

	@Override
	public float getPositionY() {
		return currentLocationY;
	}


	@Override
	public boolean setPosition(final float xf, final float yf) {
		return put(grid.pointToID(xf, yf));
	}

	@Override
	public boolean setRawGridPosition(final int x, final int y){
		return put(grid.rawXYToID(x, y));
	}

	@Override
	public GridDirection getOrientation() {
		return orientation;
	}
	
	@Override
	public boolean setOrientation(GridDirection direction) {
		if (direction == GridDirection.NEUTRAL) {
			return false;
		}
		this.orientation = direction;
		return true;
	}

	@Override
	public boolean canMove(final GridDirection direction) {
		final int newid = grid.transformIDbyDirection(currentLocationID, direction);
		if (grid.idIsOccupied(newid))
			return false;
		return true;
	}

	@Override
	public boolean move(final GridDirection direction) {
		if (direction == GridDirection.NEUTRAL) {
			return false;
		}
		this.orientation = direction;
		final int newid = grid.transformIDbyDirection(currentLocationID, direction);
		if (newid == currentLocationID || grid.idIsOccupied(newid))
			return false;
		final GOState.StateType stateType = GOState.StateType.WALKING;
		final boolean allowed = parent.getStateMananger().proposeState(stateType);
		if (!allowed) {
			return false;
		}
		final float oldX = currentLocationX;
		final float oldY = currentLocationY;
		final boolean moving = put(newid);
		if (moving) {
			movementSimulator.start(oldX, oldY, currentLocationX, currentLocationY);
		}
		return moving;
	}

	@Override
	public void turn(final GridDirection direction) {
		if (direction == GridDirection.NEUTRAL)
			return;
		orientation = direction;
	}

	protected void updateCurrentLocationPoint() {
		if (grid.isValidID(currentLocationID)) {
			currentLocationX = grid.idToPointX(currentLocationID);
			currentLocationY = grid.idToPointY(currentLocationID);
		} else {
			currentLocationX = INVALID_POSITION_FLOAT;
			currentLocationY = INVALID_POSITION_FLOAT;
		}
	}

	protected boolean put(final int locationID) {
		if (locationID == currentLocationID || grid.idIsOccupied(locationID))
			return true;
		final boolean success = grid.put(locationID, parent);
		if (!success)
			return false;
		if (grid.isValidID(currentLocationID)) {
			grid.remove(currentLocationID);
		}
		currentLocationID = locationID;
		//TODO
		updateCurrentLocationPoint();
		return true;
	}
	
	/**
	 * 
	 * @return
	 * the raw current location id
	 */
	public int getPositionID(){
		return this.currentLocationID;
	}


	/**
	 * Simulates movement between two world coordinate points according
	 * to progress (see update()) by writing directly into the
	 * currentLocation coordinates of its Spacial2D - parent.
	 * 
	 * @author til
	 *
	 */
	private class MovementSimulator {
		private static final String TAG = "Spatial2D.MovementSimulator";

		private final GOComponentSpatial2D parent;
		private float orgx;
		private float orgy;
		private float dx;
		private float dy;
		private boolean running = false;

		/**
		 * @param parent	not <code>null</code>
		 */
		public MovementSimulator(final GOComponentSpatial2D parent) {
			if (parent == null) {
				Log.w(TAG, "constructor called with 'null' parent");
				throw new IllegalArgumentException("'parent' parameter must not be null");
			}
			this.parent = parent;
		}



		/**
		 * Start simulating movement between points origin(x,y) and
		 * destination(x,y), with zero initial progress.
		 * 
		 * @param orgx
		 * @param orgy
		 * @param destx
		 * @param desty
		 */
		public void start(final float orgx, final float orgy, final float destx, final float desty) {
			final float progress = 0;
			this.orgx = orgx;
			this.orgy = orgy;
			dx = destx - orgx;
			dy = desty - orgy;
			running = true;
			update(progress);
		}

		/**
		 * Stop simulating movement and jump to destination point.
		 */
		public void stop() {
			orgx += dx;
			orgy += dy;
			dx = 0;
			dy = 0;
			running = false;
			update(0);
		}

		public boolean isActive() {
			return running;
		}

		
		/**
		 * Update parent's currentLocation coordinates reflecting the
		 * given progress of movement.
		 * 
		 * @param progress
		 */
		public void update(final float progress) {
			parent.currentLocationX = orgx + dx * progress;
			parent.currentLocationY = orgy + dy * progress;
		}

	}


	@Override
	public float getDistance(GOGameObject target) {
		float returnValue = Float.POSITIVE_INFINITY;
		GOComponentSpatial targetSpatial = (GOComponentSpatial) target.getComponent(GOComponentType.SPATIAL);
		if (targetSpatial != null) {
			float dx = this.getPositionX() - targetSpatial.getPositionX();
			float dy = this.getPositionY() - targetSpatial.getPositionY();
			returnValue = (float) Math.sqrt(dx*dx + dy*dy);
		}
		return returnValue;
	}



}
