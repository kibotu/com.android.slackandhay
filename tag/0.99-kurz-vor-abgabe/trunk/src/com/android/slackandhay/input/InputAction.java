package com.android.slackandhay.input;

import com.android.slackandhay.gameobject.component.GOComponentInput;

import android.graphics.Point;

public class InputAction {
	public static enum Action{
		ATTACK, BLOCK, RUN, MOVE
	}
	private Action action;
	private int keycode;
	private int touchbutton;
	private Point moveOffset;
	private GOComponentInput component;
	
	public InputAction(Action action, GOComponentInput component, int keycode, int touchbutton){
		this.action = action;
		this.keycode = keycode;
		this.touchbutton =  touchbutton;
		this.component = component;
	}
	
}
