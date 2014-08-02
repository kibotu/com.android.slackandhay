package com.android.slackandhay.grid;


import android.graphics.PointF;
import android.util.Log;

/**
 * A continuous piece of the world, divided into evenly distributed cells
 * of equal size.
 * 
 * Each cell can either be empty or hold exactly one object of type
 * <code>T</code>.
 * 
 * @author til
 *
 * @param <T>	the type of object which can occupy grid cells
 */
public abstract class Grid<T> {

	private static final String TAG = Grid.class.getSimpleName();

	protected final int width;
	protected final int height;
	protected final float scale;
	protected final PointF origin;

	private final T[] cells;

	// ABSTRACT

	/**
	 * Must return an array of <code>T</code> with a length of exactly
	 * <code>width * height</code> as given in the constructor.
	 * 
	 * @return
	 */
	protected abstract T[] initialize();

	// IMPLEMENTATION

	/**
	 * A continuous piece of the world, divided into evenly distributed
	 * cells of equal size.
	 * 
	 * Each cell can either be empty or hold exactly one object of type
	 * <code>T</code>.
	 * 
	 * 
	 * @param width		max horizontal number of cells in the grid
	 * @param height	max vertical number of cells in the grid
	 * @param origin	the top-left point covered by the grid, in world coordinates
	 * @param scale		max side length of a cell in world length units
	 */
	public Grid(final int width, final int height, final PointF origin, final float scale) {
		{
			if (width < 1 || height < 1)
				throw new IllegalArgumentException("'width' and 'height' must both be > 0");
			if (scale <= 0)
				throw new IllegalArgumentException("'scale' must be > 0");
			if (origin == null)
				throw new IllegalArgumentException("'origin' must not be null");
		}
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.origin = new PointF(origin.x, origin.y);
		this.cells = initialize();
		{
			if (this.cells == null)
				throw new IllegalStateException(
				"'initialize()' must return a valid array, not null");
			if (this.cells.length != this.width * this.height)
				throw new IllegalStateException(
						"'initialize()' must return an array of the same size " +
				"as the grid (array.length == grid.width * grid.height)");
		}
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public float getOriginX() {
		return this.origin.x;
	}

	public float getOriginY() {
		return this.origin.y;
	}

	public float getScale() {
		return this.scale;
	}


	/**
	 * A valid id is the id of an existing grid cell and <i>never</i>
	 * negative.
	 * 
	 * @param id
	 * @return an integer <code>&ge; 0</code>
	 */
	public boolean isValidID(final int id) {
		return id >= 0 && id < this.width * this.height;
	}

	/**
	 * True if the point given in world coordinates is inside the
	 * space covered by the grid.
	 * 
	 * @param xf
	 * @param yf
	 * @return
	 */
	public boolean pointIsInsideGrid(final float xf, final float yf) {
		if (xf < this.origin.x)
			return false;
		if (xf >= this.origin.x + this.scale * this.width)
			return false;
		if (yf < this.origin.y)
			return false;
		if (yf >= this.origin.y + this.scale * this.height)
			return false;
		return true;
	}

	/**
	 * Converts a pair of world coordinates into a valid grid id. If the
	 * given point is located outside the grid, the id of the closest
	 * grid cell is returned.
	 * 
	 * @param xf
	 * @param yf
	 * @return
	 */
	public int pointToID(final float xf, final float yf) {
		final int x = Math.round((xf - this.origin.x) / this.scale);
		final int y = Math.round((yf - this.origin.y) / this.scale);
		return rawXYToID(x, y);
		// assert isValidID()
	}

	/**
	 * <p>
	 *  Converts a valid grid id into world coordinates and stores them
	 *  in <code>targetPoint</code>.
	 *  </p><p>
	 *  This way of returning results is used to avoid instantiating PointF
	 *  and, eventually, triggering the garbage collector while the game
	 *  is running on Android.
	 *  </p>
	 * @param id
	 * @param targetPoint
	 * @deprecated use idToPointX and idToPointY instead
	 */
	@Deprecated
	public void idToPoint(final int id, final PointF targetPoint) {
		if (!isValidID(id)) {
			Log.w(TAG, "idToPoint() called with invalid id: " + id);
		}
		final float xf = (xFromID(id) + this.origin.x) * this.scale;
		final float yf = (yFromID(id) + this.origin.y) * this.scale;
		targetPoint.set(xf, yf);
	}

	public float idToPointX(final int id) {
		return (xFromID(id) + this.origin.x) * this.scale;
	}

	public float idToPointY(final int id) {
		return (yFromID(id) + this.origin.y) * this.scale;
	}


	/**
	 * Returns <code>true</code> if the grid cell closest to the given point is
	 * occupied by a T object.
	 * 
	 * @param xf
	 * @param yf
	 * @return	<code>true</code> if the grid cell closest to the given point is
	 * 			occupied by a T object.
	 */
	public boolean pointIsOccupied(final float xf, final float yf) {
		return idIsOccupied(pointToID(xf, yf));
	}

	/**
	 * Returns <code>true</code> if the cell with the given id contains
	 * an object.
	 * 
	 * @param id
	 * @return	<code>true</code> if the cell with the given id contains
	 * an object. Invalid ids will return <code>false</code>.
	 */
	public boolean idIsOccupied(final int id) {
		if (!isValidID(id)) {
			Log.w(TAG, "idIsOccupied() called with invalid id: " + id);
			return false;
		}
		return cells[id] != null;
	}

	/**
	 * Returns the object contained in the grid cell closest to the given
	 * point. If no such object exists, <code>null</code> is returned.
	 * 
	 * @param xf
	 * @param yf
	 * @return	Returns the object contained in the grid cell closest to the given
	 * 			point. If no such object exists, <code>null</code> is returned.
	 */
	public T get(final float xf, final float yf) {
		return get(pointToID(xf, yf));
	}

	/**
	 * Returns the object contained in the grid cell with the given id.
	 * If no such object exists, or if the id is invalid, <code>null</code>
	 * is returned.
	 * 
	 * @param id
	 * @return	Returns the object contained in the grid cell with the given id.
	 * 			If no such object exists, or if the id is invalid,
	 * 			<code>null</code> is returned.
	 */
	public T get(final int id) {
		if (!isValidID(id)) {
			Log.w(TAG, "get() called with invalid id: " + id);
			return null;
		}
		return cells[id];
	}


	/**
	 * Puts an object into the grid cell closest to the point given, if
	 * this cell is empty. If the cell is already occupied, the object will
	 * not be put anywhere.
	 * 
	 * @param xf
	 * @param yf
	 * @param obj
	 * @return	the id of the cell the object ended up in, or a value
	 * 			<code>&lt; 0</code>  (= an invalid id), if put was
	 * 			not successful.
	 */
	public int put(final float xf, final float yf, final T obj) {
		final int id = pointToID(xf, yf);
		return put(id, obj) == true ? id : -1;
	}


	/**
	 * Puts an object into the grid cell with the id given. If the cell
	 * is already occupied, or if the given id is invalid (doesn't exist),
	 * the object will not be put anywhere, and <code>false</code> will be
	 * returned.
	 * 
	 * @param id
	 * @param obj
	 * @return	<code>true</code> if the object was put into the desired cell.
	 */
	public boolean put(final int id, final T obj) {
		if (!isValidID(id)) {
			Log.w(TAG, "put() called with invalid id: " + id);
			return false;
		}
		if (cells[id] != null)
			return false;
		cells[id] = obj;
		return true;
	}

	/**
	 * Remove the object in the grid cell closest to the given point and
	 * return it.
	 * 
	 * @param xf
	 * @param yf
	 * @return	the removed object, or, if no object was removed,
	 * 			 <code>null</code>.
	 */
	public T remove(final float xf, final float yf) {
		return remove(pointToID(xf, yf));
	}

	/**
	 * Remove the object contained in the grid cell with the given id
	 * and return it. If the cell is not occupied, or if the id is
	 * invalid, <code>null</code> will be returned.
	 * 
	 * @param id
	 * @return	the removed object, or, if no object was removed,
	 * 			<code>null</code>.
	 */
	public T remove(final int id) {
		if (!isValidID(id)) {
			Log.w(TAG, "remove() called with invalid id: " + id);
			return null;
		}
		final T old = cells[id];
		cells[id] = null;
		return old;
	}

	/**
	 * <p>
	 * Compute a neighboring id: return the id of the grid cell bordering
	 * on the cell with id <code>startID</code> in the given direction.
	 * </p><p>
	 * If the starting cell is on the grid border and the direction points
	 * outside the grid, the id returned will belong to the starting cell
	 * or one of its neighbors on the border. If an invalid id is supplied
	 * as an argument, a value <code>&lt; 0</code> will be returned.
	 * </p>
	 * 
	 * @param startID
	 * @param direction
	 * @return	the grid id that is next in the given direction.
	 * 			<code>&lt; 0</code>  if <code>startID</code> is invalid.
	 */
	public int transformIDbyDirection(final int startID, final GridDirection direction) {
		if (!isValidID(startID))
			//Log.w(TAG, "transformIDbyDirection() called with invalid id: " + startID);
			return -1;
		final int x = xFromID(startID) + direction.xModifier;
		final int y = yFromID(startID) + direction.yModifier;
		return rawXYToID(x, y);
	}

	/**
	 * 
	 * 
	 * @param id1
	 * @param id2
	 * @return 	the square of the distance (in grid units) between
	 * 			the grid cells with the given id's, or, if an invalid id
	 * 			is provided, a negative	value.
	 */
	public int calculateSquaredDistance(final int id1, final int id2) {
		if (!isValidID(id1)) {
			Log.w(TAG, "calculateSquaredDistance() called with invalid " +
					"parameter 'id1': " + id1);
			return -1;
		}
		if (!isValidID(id2)) {
			Log.w(TAG, "calculateSquaredDistance() called with invalid " +
					"parameter 'id2': " + id2);
			return -1;
		}
		final int dx = xFromID(id2) - xFromID(id1);
		final int dy = yFromID(id2) - yFromID(id1);
		return dx*dx + dy*dy;
	}

	/**
	 * <p>
	 * Calculate the direction of the shortest path from one grid cell to another.
	 * </p><p>
	 * Starting from the cell with <code>startID</code>, this is the
	 * direction of the neighboring cell which is closest to the cell
	 * with <code>destinationID</code>.
	 * </p><p>
	 * If one of the ids is invalid, the
	 * {@linkplain GridDirection#NEUTRAL neutral} direction will be returned.
	 * </p>
	 * 
	 * @param startID
	 * @param destinationID
	 * @return	the direction towards the neighboring cell of
	 * 			the grid cell with id <code>startID</code> which is
	 * 			closest to the cell with id <code>destinationID</code>.
	 * 			{@link GridDirection#NEUTRAL} if <code>startID</code>
	 * 			equals <code>destinationID</code>, or if an invalid id is provided.
	 */
	
	//preallocate...
	private final GridDirection[] directions = GridDirection.values();
	
	public GridDirection calculateBestDirection(final int startID, final int destinationID) {
		if (!isValidID(startID)) {
			Log.w(TAG, "calculateSquaredDistance() called with invalid " +
					"parameter 'startID': " + startID);
			return GridDirection.NEUTRAL;
		}
		if (!isValidID(destinationID)) {
			Log.w(TAG, "calculateSquaredDistance() called with invalid " +
					"parameter 'destinationID': " + destinationID);
			return GridDirection.NEUTRAL;
		}
		//is now preallocated.
		//final GridDirection[] directions = GridDirection.values();
		int bestDistance = Integer.MAX_VALUE;
		GridDirection bestDirection = GridDirection.NEUTRAL;
		//preallocate...
		final int directionslength = directions.length; 
		for (int i = 0; i < directionslength; i++) {
			final int modID = transformIDbyDirection(startID, directions[i]);
			final int d = calculateSquaredDistance(modID, destinationID);
			if (d < bestDistance) {
				bestDistance = d;
				bestDirection = directions[i];
			}
		}
		return bestDirection;
	}

	/**
	 * Convert two raw grid coordinates (in grid units) to an id. Now
	 * validation or verification is done: coordinates outside the grid
	 * will be clamped to the nearest valid value.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int rawXYToID(int x, int y) {
		x = clampX(x);
		y = clampY(y);
		final int id = x + y * this.width;
		return id;
	}

	/**
	 * Extract the horizontal grid coordinate from an id. No checking is
	 * done: coordinates outside the grid may yield an invalid value.
	 * 
	 * @param id
	 * @return 	a value in raw grid units that is not adjusted for
	 * 			grid offset or scale.
	 */
	private int xFromID(final int id) {
		return id % this.width;
	}

	/**
	 * Extract the vertical grid coordinate from an id. No checking is
	 * done: an invalid id may return coordinates outside the grid.
	 * 
	 * @param id
	 * @return	a value in raw grid units that is not adjusted for
	 * 			grid offset or scale.
	 */
	private int yFromID(final int id) {
		return id / this.width;
	}

	/**
	 * Limits a value to valid horizontal grid coordinates, which
	 * is an integer between <code>0</code> and <code>width-1</code>.
	 * Values beyond these limits will be set to the nearest limit.
	 * 
	 * @param x
	 * @return	the integer between <code>0</code> and <code>width-1</code>
	 * 			closest to <code>x</code>
	 */
	private int clampX(final int x) {
		if (x < 0)
			return 0;
		if (x >= this.width)
			return this.width - 1;
		return x;
	}

	/**
	 * Limits a value to valid vertical grid coordinates, which
	 * is an integer between <code>0</code> and <code>height-1</code>.
	 * Values beyond these limits will be set to the nearest limit.
	 * 
	 * @param y
	 * @return	the integer between <code>0</code> and <code>height-1</code>
	 * 			closest to <code>y</code>
	 */
	private int clampY(final int y) {
		if (y < 0)
			return 0;
		if (y >= this.height)
			return this.height - 1;
		return y;
	}

	@Override
	public String toString() {
		return cells.toString();
	}

}
