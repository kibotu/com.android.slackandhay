package com.android.slackandhay.gameobject.component;


import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridWorld;

/**
 * A component of this type implements movement on a macro level,
 * that is, it can be given a target point in world coordinates
 * and it will do its best (which can amount to as little or as
 * much as the implementation cares) to get there.
 * 
 * None of this will make much sense without a spatial component present,
 * so it's very likely (bet on it) that one will be required.
 * 
 * @author til
 *
 */
public abstract class GOComponentMovement extends GOComponent {

	@SuppressWarnings("unused")
	private static final String TAG = GOComponentMovement.class.getSimpleName();
	private static final GOComponentType[] requiredComponents = {
		GOComponentType.SPATIAL
	};
	private static float NO_TARGET = Float.NaN;

	protected final GridWorld world;
	private final GOComponentSpatial spatial;
	private final DirectionFinder finder;
	private float targetX = NO_TARGET;
	private float targetY = NO_TARGET;

	public GOComponentMovement(final GOGameObject parent, final GridWorld grid) {
		super(GOComponentType.MOVEMENT, parent, requiredComponents);
		world = grid;
		spatial = (GOComponentSpatial) parent.getComponent(GOComponentType.SPATIAL);
		finder = new DirectionFinder(world, spatial.getPositionX(), spatial.getPositionY());
	}

	/* (non-Javadoc)
	 * @see com.android.slackandhay.gameobject.component.GOComponent#update(int)
	 * 
	 * algorithm:
	 * 
	 * IF no target set:
	 * 		RETURN
	 * WHILE no movement possible:
	 * 		find a new direction to try
	 * 		IF this direction is NULL or NEUTRAL:
	 * 			unset target
	 * 			RETURN
	 * 		IF movement in this direction possible:
	 * 			move in this direction
	 */
	@Override
	public void update(final int dt) {
		if (!hasTarget())
			return;
		finder.reset(spatial.getPositionX(), spatial.getPositionY(), targetX, targetY);
		
		boolean canMove = false;
		while (!canMove) {
			final GridDirection direction = finder.findDirection();
			if (direction == null || direction == GridDirection.NEUTRAL) {
				unSetTarget();
				finder.stop();
				return;
			}
			canMove = spatial.canMove(direction);
			if (canMove) {
				spatial.move(direction);
			} else {
				// nothing
			}
		}
	}

	public boolean hasTarget() {
		return targetX != NO_TARGET && targetY != NO_TARGET;
	}

	public void setTarget(final float xf, final float yf) {
		targetX = xf;
		targetY = yf;

	}

	public void unSetTarget() {
		targetX = NO_TARGET;
		targetY = NO_TARGET;
	}

	//	private GridDirection findDirectionToTarget() {
	//		assert this.hasTarget();
	//		int startID = world.pointToID(spatial.getPositionX(),spatial.getPositionY());
	//		int destinationID = world.pointToID(targetX,targetY);
	//		return world.calculateBestDirection(startID, destinationID);
	//	}

	private class DirectionFinder {
		private final GridWorld world;
		private int startID;
		private int destinationID;
		private GridDirection startDirection = null;
		private GridDirection lastDirection = null;
		private boolean goClockwise = true;

		/**
		 * @param world
		 * @param posX	the current horizontal position in world coordinates
		 * @param posY	the current vertical position in world coordinates
		 */
		public DirectionFinder(final GridWorld world, final float posX, final float posY) {
			this.world = world;
			startID = world.pointToID(posX, posY);
			stop();
		}

		/**
		 * Reset this directionFinder to a new starting position and target.
		 * 
		 * @param posX
		 * @param posY
		 * @param targetX
		 * @param targetY
		 */
		public void reset(final float posX, final float posY, final float targetX, final float targetY) {
			startID = world.pointToID(posX, posY);
			destinationID = world.pointToID(targetX, targetY);
			startDirection = null;
			lastDirection = null;
			goClockwise = randomClockwise();
		}

		/**
		 * Stop this directionFinder so findDirection will return NEUTRAL
		 * until reset.
		 * 
		 */
		public void stop() {
			//			destinationID = startID;
			startID = destinationID;
		}

		/**
		 * @return	the next best guess for a good direction to the
		 * 			set target; if not such direction can be found,
		 * 			the NEUTRAL direction will be returned.
		 */
		public GridDirection findDirection() {
			GridDirection direction = null;
			if (startDirection == null) {
				startDirection = world.calculateBestDirection(startID, destinationID);
				lastDirection = startDirection;
				direction = startDirection;
			} else if (lastDirection.equals(startDirection)) {
				stop();
				direction = GridDirection.NEUTRAL;
			} else {
				direction = goClockwise ?
						lastDirection.nextClockwise() :
							lastDirection.nextCounterClockwise();
						lastDirection = direction;
			}
			return direction;
		}

		/**
		 * @return <code>true</code> or <code>false</code> with equal chance.
		 */
		private boolean randomClockwise() {
			return Math.random() > 0.5 ? true : false;
		}

	}

}
