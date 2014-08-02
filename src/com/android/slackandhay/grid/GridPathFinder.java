package com.android.slackandhay.grid;


public class GridPathFinder {
	
	private Grid worldGrid;
	
	public GridPathFinder(Grid worldGrid) {
		this.worldGrid = worldGrid;
	}
	
	/**
	 * @deprecated use Grid.getDirection directly
	 * 
	 * @param start
	 * @param destination
	 * @return
	 */
	public GridDirection getNextMove(GridLocation start, GridLocation destination) {
		return worldGrid.getDirection(start, destination);
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
	public void getPath(GridDirection[] path, GridLocation start, GridLocation destination) {
		if (path == null) {
			return;
		}
		GridLocation position = worldGrid.getCell(start).getLocation();
		for (int i = 0; i < path.length; i++) {
			GridDirection direction = worldGrid.getDirection(position, destination); 
			path[i] = direction;
			position = worldGrid.getNeighboringCell(position, direction).getLocation();
		}
		
	}
}
