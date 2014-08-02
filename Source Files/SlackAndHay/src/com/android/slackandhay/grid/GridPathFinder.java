package com.android.slackandhay.grid;

/**
 * This class provides methods for finding a path in the grid
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 * 
 */
public class GridPathFinder {

	private final Grid<?> worldGrid;

	public GridPathFinder(final Grid<?> worldGrid) {
		this.worldGrid = worldGrid;
	}

	/**
	 * @deprecated use Grid.getDirection directly
	 * 
	 * @param start
	 * @param destination
	 * @return
	 */
	@Deprecated
	public GridDirection getNextMove(final int startID, final int destinationID) {
		return worldGrid.calculateBestDirection(startID, destinationID);
	}


	// TODO put this in the AI or movement component??
	/**
	 * Fills a given array with {@link GridDirection directions} that lead
	 * from one GridLocation to another. The
	 * {@link GridDirection.NEUTRAL neutral direction} indicates that
	 * either the destination has been reached or that no sensible move
	 * can be discerned.
	 * 
	 * @param path	the target array; will be completely filled
	 * 				(overwritten!) with directions.
	 * @param start	the starting location for the path
	 * @param destination	the intended destination for the path
	 */
	public void getPath(final GridDirection[] path, final int startID, final int destinationID) {
		if (path == null)
			return;
		int position = startID;
		for (int i = 0; i < path.length; i++) {
			final GridDirection direction = getNextMove(position, destinationID);
			path[i] = direction;
			position = worldGrid.transformIDbyDirection(position, direction);
		}

	}
}
