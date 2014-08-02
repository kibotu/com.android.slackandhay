package com.android.slackandhay.gameobject.component;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOState;
import com.android.slackandhay.gameobject.component.GOComponent;
import com.android.slackandhay.grid.Grid;
import com.android.slackandhay.grid.GridDirection;
import com.android.slackandhay.grid.GridLocation;

public class GOComponentMovement extends GOComponent {
	
	private final String TAG = "GenericMovementComponent";
	private static final GOComponentType[] requiredComponents = {
		GOComponentType.SPATIAL
	};
	
	private final Grid worldGrid;
	private GridLocation target = null;
	private GridDirection[] path = new GridDirection[10];
	private int pathProgress = 0;


	public GOComponentMovement(GOGameObject parent, Grid grid) {
		super(GOComponentType.MOVEMENT, parent, requiredComponents);
		this.worldGrid = grid;
	}

	@Override
	public void update(int dt) {
		if (target == null) {
			return;
		}
		final GOState state = parent.getStateMananger().getActiveState(); 
		final GOState.StateType stateType = state.getStateType();
		if (stateType != GOState.StateType.WALKING && stateType != GOState.StateType.RUNNING) {
			return;
		}
		if (pathProgress == path.length || path[pathProgress] == null) {
			// TODO getPath(path, )
		}
		
		//MUUUH
	}
	
	// copied from grid.GridPathFinder
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
	private void getPath(GridDirection[] path, GridLocation start, GridLocation destination) {
		if (path == null) {
			return;
		}
		GridLocation position = start;
		for (int i = 0; i < path.length; i++) {
			GridDirection direction = worldGrid.getDirection(position, destination); 
			path[i] = direction;
			// TODO update position
//			position = worldGrid.getNeighboringCell(position, direction).getLocation();
		}
	}


}
