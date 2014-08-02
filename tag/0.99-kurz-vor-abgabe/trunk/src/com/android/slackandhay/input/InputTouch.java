package com.android.slackandhay.input;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class InputTouch{

	private static final String TAG = InputTouch.class.getSimpleName();

	public enum BUTTON_ACTIONS{
		ATTACK, BLOCK, RUN, NONE
	};

	//will be used for map-traversal
	private final Point leftDelta = new Point(0,0);
	private final Point leftDeltaOffset = new Point(0,0);

	//could be used for gestures
	private final Point rightDelta = new Point(0,0);
	private final Point rightDeltaOffset = new Point(0,0);

	//Probably 3 buttons are used in the game:
	// ATTACK, BLOCK and RUN.
	private static final int BUTTON_COUNT = 3;
	private final InputTouchButton[] inputTouchButtons = new InputTouchButton[BUTTON_COUNT];
	private final boolean[] inputTouchButtonWasPressed = new boolean[BUTTON_COUNT];
	//	private final BUTTON_ACTIONS lastPushedButton = BUTTON_ACTIONS.NONE; TODO benutz mich :)

	private int lastLeftX = 0;
	private int lastLeftY = 0;
	private int lastRightX = 0;
	private int lastRightY = 0;

	private float SCREEN_DEVIDE_X = 512f;
	public float screenDivisionRatio = 0.66f;
	private int screenResolutionX = 1000;
	private int screenResolutionY = 600;

	private final Point[] points = new Point[2];
	private int pointerCount;

	private int leftTouchIndex = -1;
	private int rightTouchIndex = -1;

	public InputTouch(){
		//defaults....
		setScreenResolution(1000, 600);
		setScreenDevisionRatio(0.66f);
		inputTouchButtons[0] = new InputTouchButton(this, .5f, 0f, .5f, .3f);
		inputTouchButtons[1] = new InputTouchButton(this, .5f, .3f, .5f, .3f);
		inputTouchButtons[2] = new InputTouchButton(this, .5f, .6f, .5f, .4f);
		inputTouchButtonWasPressed[0] = false;
		inputTouchButtonWasPressed[1] = false;
		inputTouchButtonWasPressed[2] = false;

		//preallocation:
		points[0] = new Point(0,0);
		points[1] = new Point(0,0);

	}

	/*
	 * This Method determines what amount of the left side of the screen
	 * is counted as "map input", where 0f equals 0% and 1f equals 100% respectively.
	 */
	public void setScreenDevisionRatio(final float ratio){
		screenDivisionRatio = ratio;
		SCREEN_DEVIDE_X = screenResolutionX*screenDivisionRatio;
	}

	public void setScreenResolution(final int screenResolutionX,final int screenResolutionY){

		this.screenResolutionX = screenResolutionX;
		this.screenResolutionY = screenResolutionY;
		SCREEN_DEVIDE_X = this.screenResolutionX*screenDivisionRatio;
		Log.d(TAG, "Screen Devision Barrier set to: "+SCREEN_DEVIDE_X);

		/*for(int i=0; i<BUTTON_COUNT; i++){
    		inputTouchButtons[i].calculateAbsolutePosition();
    	}*/

	}

	public boolean onTouch( final MotionEvent event) {

		final int action = event.getAction() & MotionEvent.ACTION_MASK;
		final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		pointerCount = event.getPointerCount();
		final int actionId = event.getPointerId(pointerIndex);

		switch(action) {		
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_DOWN:
			if(event.getX(actionId)<SCREEN_DEVIDE_X){
				lastLeftX = (int)event.getX(actionId);
				lastLeftY = (int)event.getY(actionId);
				leftTouchIndex = actionId;
			} else {
				lastRightX = (int)event.getX(actionId);
				lastRightY = (int)event.getY(actionId);
				rightTouchIndex = actionId;
				for(int i = 0; i<BUTTON_COUNT; i++){
					if(inputTouchButtons[i].wasPushed(lastRightX, lastRightY)){
						inputTouchButtonWasPressed[i] = true;
						//Log.d(TAG, "Button was pushed: "+i);
					}
				}
			}
			break;			
		case MotionEvent.ACTION_MOVE:
			for (int i = 0; i < pointerCount; i++) {
				final int pointerId = event.getPointerId(i);
				points[pointerId].x = (int)event.getX(i);
				points[pointerId].y = (int)event.getY(i);

				//              lastActions[pointerId] = action;
				if(pointerId == leftTouchIndex){
					setLeftDelta((int)event.getX(pointerId)-lastLeftX,(int)event.getY(pointerId)-lastLeftY);
					lastLeftX = (int)event.getX(pointerId);
					lastLeftY = (int)event.getY(pointerId);
				}
				if(pointerId == rightTouchIndex){
					setRightDelta((int)event.getX(pointerId)-lastRightX,(int)event.getY(pointerId)-lastRightY);
					lastRightX = (int)event.getX(pointerId);
					lastRightY = (int)event.getY(pointerId);
				}
			}
			break;			
		case MotionEvent.ACTION_POINTER_UP:			
		case MotionEvent.ACTION_CANCEL:			
		case MotionEvent.ACTION_UP:			
			if(leftTouchIndex == actionId){
				lastLeftX = 0;
				lastLeftY = 0;
				leftDelta.x = 0;
				leftDelta.y = 0;
				leftTouchIndex = -1;
			}
//			if(rightTouchIndex == actionId){
				lastRightX = 0;
				lastRightY = 0;
				rightDelta.x = 0;
				rightDelta.y = 0;
				rightTouchIndex = -1;
				inputTouchButtonWasPressed[0] = false;
				inputTouchButtonWasPressed[1] = false;
				inputTouchButtonWasPressed[2] = false;
//			}
			break;
		}
//		touchView.invalidate();
		return true;
	}

	private void setLeftDelta(final int x, final int y) {
		leftDelta.x = x;
		leftDelta.y = y;
		leftDeltaOffset.x = leftDeltaOffset.x + leftDelta.x;
		leftDeltaOffset.y = leftDeltaOffset.y + leftDelta.y;
	}

	public Point getLeftDelta() {
		return leftDelta;
	}

	private void setRightDelta(final int x, final int y) {
		rightDelta.x = x;
		rightDelta.y = y;
		rightDeltaOffset.x = rightDeltaOffset.x + rightDelta.x;
		rightDeltaOffset.y = rightDeltaOffset.y + rightDelta.y;

	}

	//Preallocate...
	private final Point retp1 = new Point(0,0);
	public Point getLastLeftDelta(){
		retp1.x = leftDeltaOffset.x;
		retp1.y = leftDeltaOffset.y;
		leftDeltaOffset.x = 0;
		leftDeltaOffset.y = 0;
		return retp1;
	}
	//Preallocate...
	private final Point retp2 = new Point(0,0);
	public Point getLastRightDelta(){
		retp2.x = rightDeltaOffset.x;
		retp2.y = rightDeltaOffset.y;
		rightDeltaOffset.x = 0;
		rightDeltaOffset.y = 0;
		return retp2;
	}

	public Point getRightDelta() {
		return rightDelta;
	}

	public int getScreenResolutionX() {
		return screenResolutionX;
	}

	public int getScreenResolutionY() {
		return screenResolutionY;
	}

	/**
	 * This method checks whether a button was pressed since the last time
	 * it was checked.
	 * @param number represents the Nth button.
	 * @return whether the button was pressed during that period
	 */
	public boolean getButtonWasPressed(final int number) {
		if(number > BUTTON_COUNT){
			return false;
		}
		return inputTouchButtonWasPressed[number];
	}
}
