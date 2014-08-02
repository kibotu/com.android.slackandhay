package com.android.slackandhay.gameobject.component;

/**
 * This class is used to manage all the components of a game object
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
public class GOComponentManager {

	private GOComponentControl control;
	private GOComponentSpatial spatial;
	private GOComponentMovement movement;
	private GOComponentHealth health;
	private GOComponentOffense offense;
	private GOComponentDefense defense;
	private GOComponentGraphics graphics;
	private GOComponentSound sound;

	public GOComponentManager() {
		// MUHAHA SYNTAX ERROR (nix, comment nerf!)
	}

	public GOComponentControl getControl() {
		return control;
	}

	public GOComponentSpatial getSpatial() {
		return spatial;
	}

	public GOComponentMovement getMovement() {
		return movement;
	}

	public GOComponentHealth getHealth() {
		return health;
	}

	public GOComponentOffense getOffense() {
		return offense;
	}

	public GOComponentDefense getDefense() {
		return defense;
	}

	public GOComponentGraphics getGraphics() {
		return graphics;
	}

	public GOComponentSound getSound() {
		return sound;
	}

	public boolean hasComponent(final GOComponentType type) {
		switch (type) {
		case CONTROL:
			return control != null;
		case SPATIAL:
			return spatial != null;
		case MOVEMENT:
			return movement != null;
		case HEALTH:
			return health != null;
		case OFFENSIVE:
			return offense != null;
		case DEFENSIVE:
			return defense != null;
		case GRAPHICS:
			return graphics != null;
		case SOUND:
			return sound != null;
		default:
			return false;
		}
	}

	// fix

	/*
	 * tom: FIXED! please never ever leave your dead bodies lying around in the
	 * living room (trunk).
	 * 
	 * public GOComponent addComponent(GOComponent component) { GOComponent
	 * oldComponent = null; switch (component.type) { case CONTROL: return
	 * control != null; case SPATIAL: return spatial != null; case MOVEMENT:
	 * return movement != null; case HEALTH: return health != null; case
	 * OFFENSIVE: return offense != null; case DEFENSIVE: return defense !=
	 * null; case GRAPHICS: return graphics != null; case SOUND: return sound !=
	 * null; default: return false; }
	 * 
	 * return oldComponent; }
	 */

}
