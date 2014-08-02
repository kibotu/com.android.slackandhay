package com.android.slackandhay.gameobject;

import java.util.Vector;

import android.graphics.Point;
import android.util.Log;

import com.android.slackandhay.RenderSychronizer;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.gameobject.component.GOComponentAI;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.gameobject.component.preset.CameraComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.GOComponentFollowMovement;
import com.android.slackandhay.gameobject.component.preset.GOComponentSpatial2D;
import com.android.slackandhay.grid.GridWorld;
import com.android.slackandhay.level.Level;
import com.android.slackandhay.scene.Animation;
import com.android.slackandhay.scene.Texture;

/**
 * This class keeps track of all game objects and updates them after every frame
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class GOGameObjectManager {
	/**
	 * all game object manager share the same object vector reference as the
	 * renderer
	 **/
	private final Vector<GOGameObject> gameObjects = RenderSychronizer.getInstance().getGOGameObjects();
	private final GOGameObjectFactory factory;
	private final GridWorld gridWorld;
	private final Level level;

	private int currentStep = 0;
	private int arraySize = 0;
	private int destroyedGameObjects = 0;
	private int lastUpdate = 0;

	private final int NEXT_ENEMY_WAVE_THRESHOLD = 4;

	private final String TAG = GOGameObjectManager.class.getSimpleName();
	GOGameObject camera = null;

	/**
	 * creates a new game object manager
	 * 
	 * @param grid
	 *            the grid used by the game object manager to insert and move
	 *            game objects
	 * @param level
	 *            the level used by the game object manager
	 */
	public GOGameObjectManager(final GridWorld grid, final Level level) {
		gridWorld = grid;
		this.level = level;
		factory = new GOGameObjectFactory(gridWorld);
	}

	// Allocating here just for testing purposes:
	// TODO delete me later
	private GOGameObject player;
	private boolean playerHasDog = false;

	// ------------------------------------------

	/**
	 * creates all game objects as defined inside the level
	 */
	public void createGameObjects() {

		// preallocate
		final int spsize = level.getSpawnPoints().size();

		for (int i = 0; i < spsize; i++) {
			switch (level.getSpawnPoints().get(i).getType()) {
			case CAMERA:
				Log.v(TAG, "Adding camera to grid.");
				camera = createNewCamera();
				((GOComponentSpatial2D) camera.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).x);
				break;
			}
		}

		for (int i = 0; i < spsize; i++) {
			switch (level.getSpawnPoints().get(i).getType()) {
			case PLAYER:
				Log.v(TAG, "Adding player to grid.");
				player = createNewPlayer();

				((GOComponentSpatial2D) player.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				// Log.v(TAG,"player grid id :"+((GOComponentSpatial2D)player.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(1,1));
				if (camera != null) {
					((GOComponentFollowMovement) player.getComponent(GOComponentType.MOVEMENT))
							.setTargetSpatial(camera);
				}
				break;
			case DOG:

				final GOGameObject dog = createNewEnemyDog();
				((GOComponentSpatial2D) dog.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				// TODO delete me later
				if (player != null) {
					playerHasDog = true;
					((GOComponentAI) dog.getComponent(GOComponentType.CONTROL)).setTarget(player);
				}
				// --------------------------------
				break;
			case SOLDIER:
				final GOGameObject soldier = createNewEnemySoldier();
				((GOComponentSpatial2D) soldier.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				if (player != null) {
					// ((GOComponentFollowMovement)soldier.getComponent(GOComponentType.MOVEMENT)).setTargetSpatial(player);
					((GOComponentAI) soldier.getComponent(GOComponentType.CONTROL)).setTarget(player);
				}
				break;
			case HOUSE1:
				final GOGameObject house1 = createNewDecoration(Animation.HOUSE_1, Texture.CRONO);
				Log.i(TAG, "add house1");
				((GOComponentSpatial2D) house1.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);

				break;
			case HOUSE2:
				final GOGameObject house2 = createNewDecoration(Animation.HOUSE_2, Texture.CRONO);
				Log.i(TAG, "add house2");
				((GOComponentSpatial2D) house2.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE3:
				final GOGameObject house3 = createNewDecoration(Animation.HOUSE_3, Texture.CRONO);
				Log.i(TAG, "add house3");
				((GOComponentSpatial2D) house3.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE4:
				final GOGameObject house4 = createNewDecoration(Animation.HOUSE_4, Texture.CRONO);
				Log.i(TAG, "add house4");
				((GOComponentSpatial2D) house4.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE5:
				final GOGameObject house5 = createNewDecoration(Animation.HOUSE_5, Texture.CRONO);
				Log.i(TAG, "add house5");
				((GOComponentSpatial2D) house5.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE6:
				final GOGameObject house6 = createNewDecoration(Animation.HOUSE_6, Texture.CRONO);
				Log.i(TAG, "add house6");
				((GOComponentSpatial2D) house6.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE7:
				final GOGameObject house7 = createNewDecoration(Animation.HOUSE_7, Texture.CRONO);
				Log.i(TAG, "add house7");
				((GOComponentSpatial2D) house7.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE8:
				final GOGameObject house8 = createNewDecoration(Animation.HOUSE_8, Texture.CRONO);
				Log.i(TAG, "add house8");
				((GOComponentSpatial2D) house8.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE9:
				final GOGameObject house9 = createNewDecoration(Animation.HOUSE_9, Texture.CRONO);
				Log.i(TAG, "add house9");
				((GOComponentSpatial2D) house9.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case HOUSE10:
				final GOGameObject house10 = createNewDecoration(Animation.HOUSE_10, Texture.CRONO);
				Log.i(TAG, "add house10");
				((GOComponentSpatial2D) house10.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION1:
				final GOGameObject decoration1 = createNewDecoration(Animation.DECORATION_TREE_1, Texture.CRONO);
				Log.i(TAG, "add tree 1");
				((GOComponentSpatial2D) decoration1.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION2:
				final GOGameObject decoration2 = createNewDecoration(Animation.DECORATION_TREE_2, Texture.CRONO);
				Log.i(TAG, "add tree 2");
				((GOComponentSpatial2D) decoration2.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION3:
				final GOGameObject decoration3 = createNewDecoration(Animation.DECORATION_FOUNTAIN, Texture.CRONO);
				Log.i(TAG, "add fountain");
				((GOComponentSpatial2D) decoration3.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION4:
				final GOGameObject decoration4 = createNewDecoration(Animation.DECORATION_BLUB, Texture.CRONO);
				Log.i(TAG, "add decoration blub 1");
				((GOComponentSpatial2D) decoration4.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION5:
				final GOGameObject decoration5 = createNewDecoration(Animation.DECORATION_BLUB_2, Texture.CRONO);
				Log.i(TAG, "add decoration blub 2");
				((GOComponentSpatial2D) decoration5.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION6:
				final GOGameObject decoration6 = createNewDecoration(Animation.DECORATION_BLUB_3, Texture.CRONO);
				Log.i(TAG, "add decoration blub 3");
				((GOComponentSpatial2D) decoration6.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION7:
				final GOGameObject decoration7 = createNewDecoration(Animation.DECORATION_BLUB_4, Texture.CRONO);
				Log.i(TAG, "add decoration blub 4");
				((GOComponentSpatial2D) decoration7.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION8:
				final GOGameObject decoration8 = createNewDecoration(Animation.DECORATION_BLUB_5, Texture.CRONO);
				Log.i(TAG, "add decoration blub 5");
				((GOComponentSpatial2D) decoration8.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION9:
				final GOGameObject decoration9 = createNewDecoration(Animation.DECORATION_BLUB_6, Texture.CRONO);
				Log.i(TAG, "add decoration blub 6");
				((GOComponentSpatial2D) decoration9.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case DECORATION10:
				final GOGameObject decoration10 = createNewDecoration(Animation.DECORATION_BLUB_7, Texture.CRONO);
				Log.i(TAG, "add decoration blub 7");
				((GOComponentSpatial2D) decoration10.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(level
						.getSpawnPoints().get(i).x, level.getSpawnPoints().get(i).y);
				break;
			case GRASS:
				final GOGameObject grass = createNonGridMember(level.getSpawnPoints().get(i).x, level.getSpawnPoints()
						.get(i).y, level.getSpawnPoints().get(i).width, level.getSpawnPoints().get(i).height,
						Texture.GRASSBG);
				Log.i(TAG, "add grass");
				break;
			case VINTAGE:
				final GOGameObject vintage = createNonGridMember(level.getSpawnPoints().get(i).x, level
						.getSpawnPoints().get(i).y, level.getSpawnPoints().get(i).width,
						level.getSpawnPoints().get(i).height, Texture.VINTAGE);
				Log.i(TAG, "add vintage");
				break;
			case STONE:
				final GOGameObject stone = createNonGridMember(level.getSpawnPoints().get(i).x, level.getSpawnPoints()
						.get(i).y, level.getSpawnPoints().get(i).width, level.getSpawnPoints().get(i).height,
						Texture.STONE);
				Log.i(TAG, "add stone");
				break;
			}
		}
	}

	/**
	 * Creates a new Player object and adds it to the game object list, sets the
	 * appropriate textures
	 * 
	 * @return the newly created player
	 */
	public GOGameObject createNewPlayer() {
		final GOGameObject go = factory.createPlayer();
		final GOComponentGraphics graphic = (GOComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		graphic.proposeTexture(Texture.CRONO);
		graphic.setRotation(45, 1f, 0, 0);
		graphic.setScale(3, 3, 0);
		addGameObject(go);
		return go;
	}

	/**
	 * creates a new camera and adds it to the game objects list
	 * 
	 * @return the newly created camera
	 */
	public GOGameObject createNewCamera() {
		final GOGameObject go = factory.createCamera();
		final CameraComponentGraphics graphic = (CameraComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		addGameObject(go);
		return go;
	}

	/**
	 * Creates a non-grid member, adds it to the game object list and adds its
	 * texture to the graphics component
	 * 
	 * @param x
	 *            the x posistion of the object
	 * @param y
	 *            the y posistion of the object
	 * @param width
	 *            the width of the object
	 * @param height
	 *            the height of the object
	 * @param texture
	 *            the texture of the object
	 * @return the newly created object
	 */
	public GOGameObject createNonGridMember(float x, float y, float width, float height, Texture texture) {
		final GOGameObject go = factory.createNonGridMember();
		final GOComponentGraphics graphic = (GOComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		graphic.proposeTexture(texture);
		graphic.setImage(Animation.TEXTURE_REPEAT.values[1]);
		// graphic.setAbsPosition(width/2f-0.05f, height/2f-0.05f, -1.05f);
		graphic.setAbsPosition(x * 0.0625f + 0.55f, y * 0.0625f + 0.55f, -1.1f);
		// graphic.setDimension(0.6f,0.6f,0);
		graphic.setDimension(width, height, 0);
		addGameObject(go);
		return go;
	}

	/**
	 * creates a new dog
	 * 
	 * @return a dog
	 */
	public GOGameObject createNewEnemyDog() {
		final GOGameObject go = factory.createEnemyDog();
		final GOComponentGraphics graphic = (GOComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		graphic.proposeTexture(Texture.DOG);
		graphic.setRotation(45, 1f, 0, 0);
		addGameObject(go);
		return go;
	}

	/**
	 * creates a soldier
	 * 
	 * @return a soldier
	 */
	public GOGameObject createNewEnemySoldier() {
		final GOGameObject go = factory.createEnemySoldier();
		final GOComponentGraphics graphic = (GOComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		graphic.proposeTexture(Texture.SOLDIER);
		graphic.setRotation(45, 1f, 0, 0);
		addGameObject(go);
		return go;
	}

	/**
	 * creates a decoration
	 * 
	 * @param animation
	 * @param texture
	 * @return a decoration
	 */
	public GOGameObject createNewDecoration(Animation animation, Texture texture) {
		final GOGameObject go = factory.createDecoration(animation);
		final GOComponentGraphics graphic = (GOComponentGraphics) go.getComponent(GOComponentType.GRAPHICS);
		graphic.proposeTexture(texture);
		graphic.setRotation(45, 1f, 0, 0);
		graphic.setScale(3f, 3f, 0);
		addGameObject(go);
		return go;
	}

	/**
	 * adds a game object to the game object list
	 * 
	 * @param go
	 *            the game object to add
	 */
	public void addGameObject(final GOGameObject go) {
		gameObjects.add(go);
		arraySize = gameObjects.size();
	}

	/**
	 * removes a game object fromthe game object list
	 * 
	 * @param go
	 *            the game object to remove
	 */
	public void removeGameObject(final GOGameObject go) {
		gameObjects.remove(go);
		arraySize = gameObjects.size();
	}

	/**
	 * this method recycles one game object at a time, to balance the CPU load
	 * over time
	 */
	public void recycleStepByStep() {
		recycle(gameObjects.elementAt(currentStep));
		currentStep++;
		if (currentStep >= arraySize) {
			currentStep = 0;
		}
	}

	/**
	 * this method recycles a game object, meaning that dead game objects are
	 * collected, so that they can be used again the method also triggers the
	 * revival of dead game objects once the number of destroyed objects is
	 * greater than the NEXT_ENEMY_WAVE_THROSHOLD by calling
	 * resetDestroyedGameObjects
	 * 
	 * @param go
	 *            the game object you are trying to recycle
	 */
	public void recycle(final GOGameObject go) {
		if (go.getStateMananger().getActiveState().getStateType() == GOState.StateType.DEAD) {
			go.getStateMananger().proposeState(GOState.StateType.DESTROYED);
			destroyedGameObjects++;
		}
		if (destroyedGameObjects > NEXT_ENEMY_WAVE_THRESHOLD) {
			resetDestroyedGameObjects();
		}
	}

	/**
	 * revives destroyed game objects and gives them a random free position on
	 * the grid
	 */
	private void resetDestroyedGameObjects() {
		for (final GOGameObject go : gameObjects) {
			if (go.getStateMananger().getActiveState().getStateType() == StateType.DESTROYED) {
				final Point p = level.getUnoccupiedGridPoint();
				if (p != null) {
					((GOComponentSpatial2D) go.getComponent(GOComponentType.SPATIAL)).setRawGridPosition(p.x, p.y);
				}
			}
		}
	}

	/**
	 * the update method of the game object manager calls recycleStepByStep
	 * after a certain amount of time
	 * 
	 * @param dt
	 *            the time that has passed in milliseconds
	 */
	public void update(final int dt) {
		lastUpdate += dt;
		if (lastUpdate > 500) {
			recycleStepByStep();
		}
	}

	/**
	 * 
	 * @return the game object list as vector
	 */
	public Vector<GOGameObject> getGameObjects() {
		return gameObjects;
	}
}
