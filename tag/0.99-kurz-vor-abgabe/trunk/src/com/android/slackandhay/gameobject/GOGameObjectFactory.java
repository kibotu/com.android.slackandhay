package com.android.slackandhay.gameobject;

import com.android.slackandhay.gameobject.component.preset.CameraComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.CameraComponentInput;
import com.android.slackandhay.gameobject.component.preset.EnemyDogComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.EnemyDogStateManager;
import com.android.slackandhay.gameobject.component.preset.EnemySoldierComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.GOComponentFollowMovement;
import com.android.slackandhay.gameobject.component.preset.GOComponentSpatial2D;
import com.android.slackandhay.gameobject.component.preset.GenericComponentDefense;
import com.android.slackandhay.gameobject.component.preset.GenericComponentHealth;
import com.android.slackandhay.gameobject.component.preset.GenericComponentOffense;
import com.android.slackandhay.gameobject.component.preset.DecorationComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.DecorationStateManager;
import com.android.slackandhay.gameobject.component.preset.NonGridMemberComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.NonGridMemberStateManager;
import com.android.slackandhay.gameobject.component.preset.PlayerComponentGraphics;
import com.android.slackandhay.gameobject.component.preset.PlayerComponentInput;
import com.android.slackandhay.gameobject.component.preset.PlayerComponentSound;
import com.android.slackandhay.gameobject.component.preset.PlayerStateManager;
import com.android.slackandhay.gameobject.component.preset.TargetPointComponentSpatial2D;
import com.android.slackandhay.gameobject.component.preset.TargetPointStateManager;
import com.android.slackandhay.grid.GridWorld;
import com.android.slackandhay.scene.Animation;

/**
 * Creates Subclasses of the Components and the State Manager to create a new Game Object
 * @author tom
 *
 */

public class GOGameObjectFactory {

	@SuppressWarnings("unused")
	private static final String TAG = GOGameObjectFactory.class.getSimpleName();
	private final GridWorld grid;

	public GOGameObjectFactory(final GridWorld grid) {
		this.grid = grid;
	}

	//############################
	//  PLAYER FACTORY
	//############################

	private class GOPlayer extends GOGameObject{
		public GOPlayer() {
			super(new PlayerStateManager());
			//	Log.i(TAG, "Creating Player Object..." );
			//Additional Components....
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new PlayerComponentGraphics(this));
//			addComponent(new EnemyDogComponentGraphics(this));
//			addComponent(new EnemySoldierComponentGraphics(this));
			addComponent(new PlayerComponentInput(this));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new PlayerComponentSound(this));
			addComponent(new GenericComponentHealth(23, this));
			addComponent(new GenericComponentOffense(this,grid, 10));
			addComponent(new GenericComponentDefense(this, 2));

		}
	}

	public GOGameObject createPlayer(){
		return new GOPlayer();
	}

	//############################
	//      CAMERA FACTORY
	//############################

	private class GOCamera extends GOGameObject{
		protected GOCamera() {
			super(new TargetPointStateManager());
			addComponent(new CameraComponentGraphics(this));
			addComponent(new CameraComponentInput(this));
			addComponent(new TargetPointComponentSpatial2D(this, grid));
		}
	}

	public GOGameObject createCamera(){
		return new GOCamera();
	}
	
	//############################
	//      DECORATION FACTORY
	//############################
	
	private class GODecoration extends GOGameObject{
		protected GODecoration(Animation animation) {
			super(new DecorationStateManager());
			addComponent(new DecorationComponentGraphics(this,animation));
			addComponent(new GOComponentSpatial2D(this, grid));
		}
	}

	public GOGameObject createDecoration(Animation animation){
		return new GODecoration(animation);
	}

	//############################
	//     MONSTER 1 FACTORY (Monsters Inc.)
	//############################

	private class GOEnemyDog extends GOGameObject{
		protected GOEnemyDog() {
			super(new EnemyDogStateManager());
			addComponent(new EnemyDogComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new GenericComponentHealth(23, this));
			addComponent(new GenericComponentOffense(this,grid, 10));
			addComponent(new GenericComponentDefense(this, 2));
		}
	}

	public GOGameObject createEnemyDog(){
		return new GOEnemyDog();
	}
	
	private class GOEnemySoldier extends GOGameObject{
		protected GOEnemySoldier() {
			super(new EnemyDogStateManager());
			addComponent(new EnemySoldierComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
			addComponent(new GOComponentFollowMovement(this, grid));
			addComponent(new GenericComponentHealth(23, this));
			addComponent(new GenericComponentOffense(this,grid, 10));
			addComponent(new GenericComponentDefense(this, 2));
		}
	}

	public GOGameObject createEnemySoldier(){
		return new GOEnemySoldier();
	}

	//############################
	//     NonGrid Member FACTORY
	//############################

	private class GONonGridMember extends GOGameObject{
		protected GONonGridMember() {
			super(new NonGridMemberStateManager());
			addComponent(new NonGridMemberComponentGraphics(this));
			addComponent(new GOComponentSpatial2D(this, grid));
		}
	}

	public GOGameObject createNonGridMember(){
		return new GONonGridMember();
	}
}
