package com.android.slackandhay.gameobject;

import com.android.slackandhay.gameobject.component.preset.*;
import com.android.slackandhay.grid.GridWorld;
import com.android.slackandhay.scene.Animation;

/**
 * Creates Subclasses of the Components and the State Manager to create a new
 * Game Object
 * 
 * @author tom
 * 
 */

public class GOGameObjectFactory {
	/**
	 * This class is used to create all necessary game object. game object
	 * constructors should never be called anywhere else that here.
	 */

	@SuppressWarnings("unused")
	private static final String TAG = GOGameObjectFactory.class.getSimpleName();
	private final GridWorld grid;

	/**
	 * creates a now game object factory
	 * 
	 * @param grid
	 *            the grid used to position the game obejcts inside the world
	 */
	public GOGameObjectFactory(final GridWorld grid) {
		this.grid = grid;
	}

	// ############################
	// PLAYER FACTORY
	// ############################

	/**
	 * the instructions needed to create a player object
	 */
	private class GOPlayer extends GOGameObject {
		public GOPlayer() {
			super(new PlayerStateManager());
			// Log.i(TAG, "Creating Player Object..." );
			// Additional Components....
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new PlayerComponentGraphics(this));
			// addComponent(new EnemyDogComponentGraphics(this));
			// addComponent(new EnemySoldierComponentGraphics(this));
			addComponent(new PlayerComponentInput(this));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new PlayerComponentSound(this));
			addComponent(new GenericComponentHealth(2000, this));
			addComponent(new GenericComponentOffense(this, grid, 10));
			addComponent(new GenericComponentDefense(this, 2));

		}
	}

	/**
	 * this method calls the GOPlayer constructor internally
	 * 
	 * @return a new player object
	 */
	public GOGameObject createPlayer() {
		return new GOPlayer();
	}

	// ############################
	// CAMERA FACTORY
	// ############################
	/**
	 * the instructions needed to create a camera object
	 */
	private class GOCamera extends GOGameObject {
		protected GOCamera() {
			super(new TargetPointStateManager());
			addComponent(new CameraComponentGraphics(this));
			addComponent(new CameraComponentInput(this));
			addComponent(new TargetPointComponentSpatial2D(this, grid));
		}
	}

	/**
	 * creates a new camera object, which is used to determine the position of
	 * the camera inside the game
	 * 
	 * @return
	 * 		a camera game object
	 */
	public GOGameObject createCamera() {
		return new GOCamera();
	}

	// ############################
	// DECORATION FACTORY
	// ############################
	/**
	 * the instructions needed to create a decoration object
	 */
	private class GODecoration extends GOGameObject {
		protected GODecoration(Animation animation) {
			super(new DecorationStateManager());
			addComponent(new DecorationComponentGraphics(this, animation));
			addComponent(new GOComponentSpatial2D(this, grid));
		}
	}

	/**
	 * This method creates a new decoration, such as a house or a well
	 * @param animation
	 * 		an animation used to represent the decoration graphically in the renderer
	 * @return
	 * 		a new decoration game object such as a house or a well or a tree
	 */
	public GOGameObject createDecoration(Animation animation) {
		return new GODecoration(animation);
	}

	// ############################
	// MONSTER 1 FACTORY (Monsters Inc.)
	// ############################
	/**
	 * the instructions needed to create a enemy dog object
	 */
	private class GOEnemyDog extends GOGameObject {
		protected GOEnemyDog() {
			super(new EnemyDogStateManager());
			addComponent(new EnemyDogComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new GenericComponentHealth(23, this));
			addComponent(new GenericComponentOffense(this, grid, 10));
			addComponent(new GenericComponentDefense(this, 2));
			addComponent(new GenericComponentAI(this));
		}
	}

	/**
	 * creates a new hostile dog 
	 * @return
	 * 		a hostile dog 
	 */
	public GOGameObject createEnemyDog() {
		return new GOEnemyDog();
	}

	/**
	 * the instructions needed to create a enemy soldier object
	 */
	private class GOEnemySoldier extends GOGameObject {
		protected GOEnemySoldier() {
			super(new EnemyDogStateManager());
			addComponent(new EnemySoldierComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new GenericComponentHealth(23, this));
			addComponent(new GenericComponentOffense(this, grid, 10));
			addComponent(new GenericComponentDefense(this, 2));
			addComponent(new GenericComponentAI(this));
		}
	}

	/**
	 * creates a new hostile soldier
	 * @return
	 * 		a new hostile soldier
	 */
	public GOGameObject createEnemySoldier() {
		return new GOEnemySoldier();
	}

	// ############################
	// NonGrid Member FACTORY
	// ############################
	/**
	 * the instructions needed to create a non grid member object
	 */
	private class GONonGridMember extends GOGameObject {
		protected GONonGridMember() {
			super(new NonGridMemberStateManager());
			addComponent(new NonGridMemberComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
		}
	}

	/**
	 * creates a game object which is not inside the game grid 
	 * @return
	 * 		a new non grid member object
	 */
	public GOGameObject createNonGridMember() {
		return new GONonGridMember();
	}
}
