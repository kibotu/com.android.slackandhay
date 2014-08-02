package com.android.slackandhay.grid;

import android.graphics.PointF;

import com.android.slackandhay.gameobject.GOGameObject;

public class Grid {
	
	private final GridCell[][] cells;
	private final PointF origin;
	private final int pixelsPerCell;
	private final int width;
	private final int height;
	
	/**
	 * @param width number of cells per grid row
	 * @param height number of cells per grid column
	 * @param location	the real-world position of the top-left grid cell
	 * @param pixelsPerUnit side length of a grid cell in pixels
	 */
	public Grid(int width, int height, PointF location, int pixelsPerUnit) {
				if (width <= 0 || height <= 0) {
					throw new IllegalArgumentException("'width' and 'height' must be > 0");
				}
				if (location == null) {
					throw new IllegalArgumentException("'location' must not be null");
				}
				if (pixelsPerUnit <= 0) {
					throw new IllegalArgumentException("'pixelsPerUnit' must be > 0");
				}
				this.origin = location;
				this.pixelsPerCell = pixelsPerUnit;
				this.width = width;
				this.height = height;
				this.cells = new GridCell[width][height];
				initialize();
	}
	
	/**
	 * Returns a measurement for the distance between two given locations,
	 * which must not necessarily be linear. I really oughta be more 
	 * specific... 
	 * 
	 * @param firstLocation 
	 * @param secondLocation
	 * @return the square of the distance between two given GridLocations
	 */
	public int getDistance(GridLocation firstLocation, GridLocation secondLocation) {
		// TODO null checks. would returning a distance of 0 do more good than harm?
		final int dx = firstLocation.x - secondLocation.x;
		final int dy = firstLocation.y - secondLocation.y;
		return dx*dx + dy*dy;
	}
	
	/**
	 * Given a starting gridLocation, returns the direction of the neighboring
	 * gridLocation that is closest to the destination. This direction may
	 * of course be {@linkplain GridDirection.NEUTRAL neutral}; apart from 
	 * the obvious case of <code>start == destination</code>, this will happen
	 * if no sensical direction can be determined, for example if start or
	 * destination happen to lie outside the grid.
	 * 
	 * @param start
	 * @param destination
	 * @return
	 */
	public GridDirection getDirection(GridLocation start, GridLocation destination) {
		if (locationOutOfBounds(start) || locationOutOfBounds(destination)) {
			return GridDirection.NEUTRAL;
		}
		int bestValue = Integer.MAX_VALUE;
		GridDirection bestDirection = GridDirection.NEUTRAL;
		final GridDirection[] directions = GridDirection.values();
		for (int i = 0; i < directions.length; i++) {
			final GridDirection dir = directions[i];
			final int dx = destination.x - (start.x + dir.xModifier);
			final int dy = destination.y - (start.y + dir.yModifier);
			final int value = dx*dx + dy*dy;
			if (value < bestValue) {
				bestValue = value;
				bestDirection = dir;
			}
		}
		return bestDirection;
	}
	
	/**
	 * Returns the gameObject occupying a given gridLocation.
	 * 
	 * @param location
	 * @return	the gameObject at location, or <code>null</code>
	 */
	public GOGameObject get(GridLocation location) {
		return getCell(location).getOccupant();
	}
	
	public GridLocation getNeighboringLocation(GridLocation location, GridDirection direction) {
		return getNeighboringCell(location, direction).getLocation();
	}
	
	/**
	 * Deals damage to a given gridLocation. This damage will not accrue
	 * but be instantly absorbed, which makes a difference to some details
	 * of the game mechanics. A theoretical scenario not necessarily applicable
	 * to this game: 
	 * 
	 * Imagine a location receiving some damage which is fatal to the 
	 * object sitting there; almost instantly thereafter, negative damage 
	 * (= healing) is applied. Since the object already absorbed the fatal
	 * damage, healing will have no effect; otherwise, the negative damage
	 * could have cancelled some of the real damage, and the object might
	 * have survived this example.
	 * 
	 * @param damage
	 * @param location
	 */
	public void putDamage(int damage, GridLocation location) {
		// TODO
		final GridCell target = getCell(location);
		if (target == null) {
			return;
		}
		// target.components.defense.takeDamage(damage, null);
	}
	
	/**
	 * Place a gameObject in a girdLocation. This will work if no other
	 * gameObject is currently sitting there.
	 * 
	 * @param location
	 * @param object
	 * @return true if the object is now located at location
	 */
	boolean put(GridLocation location, GOGameObject object) {
		//TODO null checks
		//TODO if location.equals(object.location) return true
		final GridCell target = getCell(location);
		if (target == null || target.isOccupied()) {
			return false;
		}
		target.occupy(object);
		return true;
	}
	
	/**
	 * Remove the gameObject from a given location.
	 * 
	 * @param location
	 * @return	the gameObject that was removed; <code>null</code> if there
	 * 			was nothing there to remove 
	 */
	GOGameObject unPut(GridLocation location) {
		final GridCell target = getCell(location);
		return (target == null || !target.isOccupied()) ? null : target.getOccupant();
	}

	GridCell getCell(GridLocation location) {
		final int x = location.x;
		final int y = location.y;
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return null;
		}
		return cells[x][y];
	}
	
	GridCell getNeighboringCell(GridLocation location, GridDirection direction) {
		final int x = location.x + direction.xModifier;
		final int y = location.y + direction.yModifier;
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return null;
		}
		return cells[x][y];
	}
	
	private void initialize() {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				float xf = (x + this.origin.x) * this.pixelsPerCell;
				float yf = (y + this.origin.y) * this.pixelsPerCell;
				cells[x][y] = new GridCell(new GridLocation(x,y, new PointF(xf, yf)));
			}
		}
	}

	private boolean locationOutOfBounds(GridLocation location) {
		return (location.x < 0 || location.y < 0 || 
				location.x >= this.width || location.y >= this.height);
	}
	
}
