package com.android.slackandhay.input;

import java.util.HashMap;

import android.graphics.Point;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

public class InputSystem  implements OnTouchListener, OnKeyListener, OnClickListener{
	
	public static final String TAG = "InputSystem";
	
	//HashMap<InputType, InputAction> inputActionTable = new HashMap<InputType, InputAction>();
	
	InputTouch inputTouch = new InputTouch();
	InputKeyboard inputKeyboard = new InputKeyboard();
	public InputSystem(){
		 
	}
	
	public void setResolution(int screenResolutionX, int screenResolutionY){
		inputTouch.setScreenResolution(screenResolutionX, screenResolutionY);
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final boolean touchHandled = inputTouch.onTouch(event);		
		//Log.d(TAG, "[Touch left] x:"+inputTouch.getLeftDelta().x+" y:"+inputTouch.getLeftDelta().y );
		//Log.d(TAG, "[Touch right] x:"+inputTouch.getRightDelta().x+" y:"+inputTouch.getRightDelta().y );
		return touchHandled;
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return inputKeyboard.onKey(event);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Create class to cope with mouse input... maybe.	
	}
	
	public Point getMapOffsetPoint(){
		return inputTouch.getLeftDelta();
	}
	public boolean buttonWasPressed(int buttonnr){
		return inputTouch.getButtonWasPressed(buttonnr);
	}
}
