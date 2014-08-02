package com.android.slackandhay.gameobject.component;



import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridWorld;

/**
 * This component provides a location and an orientation in space,
 * which is currently limited to two dimensions. It is also responsible
 * for movement on the basic (or microscopic) level, in that it defines
 * the length of an atomic step of movement and is also able to effect
 * such movement steps. The orientation the component holds is independent
 * of the direction of such movement.
 * 
 * @author til
 *
 */
public abstract class GOComponentSpatial extends GOComponent {
	
	protected final GridWorld grid;

	public GOComponentSpatial(final GOGameObject parent, final GridWorld grid) {
		super(GOComponentType.SPATIAL, parent);
		this.grid = grid;
	}

	public GOComponentSpatial(final GOGameObject parent, final GridWorld grid,
			final GOComponentType[] requiredComponents) {
		super(GOComponentType.SPATIAL, parent, requiredComponents);
		this.grid = grid;
	}


	@Override
	public void update(final int dt) {
		// TODO Auto-generated method stub

	}

	/**
	 * <code>true</code> if this component actually holds a position.
	 * 
	 * @return <code>true</code> if this component actually holds a position.
	 * @deprecated	this should actually be done via the component's
	 * 				<code>active</code>/<code>inactive</code> states
	 * 				(functionality not yet implemented ;) )
	 */
	@Deprecated
	public abstract boolean hasPosition();

	/**
	 * <code>True</code> if the parent gameObject occupies the point
	 * with coordinates <code>(x,y)</code>.
	 * 
	 * @param x
	 * @param y
	 * @return	<code>true</code> if the parent gameObject occupies the point
	 * 			with coordinates <code>(x,y)</code>
	 */
	public abstract boolean isAtPosition(float x, float y);


	/**
	 * @return	the horizontal position of the parent gameObject in world
	 * 			units; NaN means there is no position
	 */
	public abstract float getPositionX();
	/**
	 * @return	the vertical position of the parent gameObject in world
	 * 			units; NaN means there is no position
	 */
	public abstract float getPositionY();
	/**
	 * Try to put the parent gameObject into the position defined by
	 * the given coordinates. Returns <code>true</code> if the operation
	 * was successful.
	 * 
	 * @param xf
	 * @param yf
	 * @return	<code>true</code> if the component's parent gameObject
	 * 			occupies the position with coordinates <code>(x,y)</code>.
	 */
	public abstract boolean setPosition(float xf, float yf);
	/**
	 * @return	the direction the parent gameObject is turned towards
	 */
	public abstract GridDirection getOrientation();
	public abstract boolean setOrientation(GridDirection direction);
	/**
	 * @param direction
	 * @return	<code>true</code> if the parent object can move
	 * 			in the given direction.
	 */
	public abstract boolean canMove(GridDirection direction);
	/**
	 * Try to move the parent gameObject into the given direction.
	 * Returns <code>true</code> if the operation was successful.
	 * 
	 * @param xf
	 * @param yf
	 * @return	<code>true</code> if the component's parent gameObject
	 * 			completed the move.
	 */
	public abstract boolean move(GridDirection direction);
	/**
	 * Changes the parent gameObject's orientation to the one
	 * given by <code>direction</code>.
	 * 
	 * @param direction
	 */
	public abstract void turn(GridDirection direction);

	public abstract boolean setRawGridPosition(int x, int y);
	
	public abstract float getDistance(GOGameObject target);

}
