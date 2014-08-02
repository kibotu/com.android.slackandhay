package com.android.slackandhay.input;

import com.android.slackandhay.gameobject.component.GOComponentInput;

import android.graphics.Point;

/**
 * not implemented yet (should provide a more flexible user input in the future)
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */

public class InputAction {
	public static enum Action {
		ATTACK, BLOCK, RUN, MOVE
	}

	private Action action;
	private int keycode;
	private int touchbutton;
	private Point moveOffset;
	private GOComponentInput component;

	public InputAction(Action action, GOComponentInput component, int keycode, int touchbutton) {
		this.action = action;
		this.keycode = keycode;
		this.touchbutton = touchbutton;
		this.component = component;
	}

}
