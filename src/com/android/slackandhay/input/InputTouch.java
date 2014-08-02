package com.android.slackandhay.input;

import java.lang.reflect.Array;


import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputTouch extends InputType{

	private static final String TAG = "InputTouch"; 
	
	public enum BUTTON_ACTIONS{
		ATTACK, BLOCK, RUN, NONE
	};
	
	//will be used for map-traversal
	private Point leftDelta = new Point();
	
	//could be used for gestures
	private Point rightDelta = new Point();
	
	//Probably 3 buttons are used in the game:
	// ATTACK, BLOCK and RUN.
	private static final int BUTTON_COUNT = 3;
	private InputTouchButton[] inputTouchButtons = new InputTouchButton[BUTTON_COUNT];
	private boolean[] inputTouchButtonWasPressed = new boolean[BUTTON_COUNT]; 
	private BUTTON_ACTIONS lastPushedButton = BUTTON_ACTIONS.NONE; 
	
	private int lastLeftX = 0;
	private int lastLeftY = 0;
	private int lastRightX = 0;
	private int lastRightY = 0;
	
	private static final int MAX_POINTERS = 2;

	private float SCREEN_DEVIDE_X = 512f;
	public float screenDivisionRatio = 0.66f;
	private int screenResolutionX = 1000;
	private int screenResolutionY = 600;

    private Paint paint;
    private Paint paintInfoText;

    private Point[] points = new Point[2];
    private int pointerCount;

    
    private int leftTouchIndex = -1;
    private int rightTouchIndex = -1;
	
    public InputTouch(){
    	//defaults....
    	this.setScreenResolution(1000, 600);
    	this.setScreenDevisionRatio(0.66f);
    	this.inputTouchButtons[0] = new InputTouchButton(this, .5f, 0f, .5f, .3f);
    	this.inputTouchButtons[1] = new InputTouchButton(this, .5f, .3f, .5f, .3f);
    	this.inputTouchButtons[2] = new InputTouchButton(this, .5f, .6f, .5f, .4f);
    	this.inputTouchButtonWasPressed[0] = false;
    	this.inputTouchButtonWasPressed[1] = false;
    	this.inputTouchButtonWasPressed[2] = false;
    	
    }
    
    /*
     * This Method determines what amount of the left side of the screen
     * is counted as "map input", where 0f equals 0% and 1f equals 100% respectively.
     */
    public void setScreenDevisionRatio(float ratio){
    	screenDivisionRatio = ratio;
    	SCREEN_DEVIDE_X = screenResolutionX*screenDivisionRatio;
    }
    
    public void setScreenResolution(int screenResolutionX,int screenResolutionY){
    	
    	this.screenResolutionX = screenResolutionX;
    	this.screenResolutionY = screenResolutionY;
    	this.SCREEN_DEVIDE_X = (this.screenResolutionX*screenDivisionRatio);
    	Log.d(TAG, "Screen Devision Barrier set to: "+this.SCREEN_DEVIDE_X);
    	
    	/*for(int i=0; i<BUTTON_COUNT; i++){
    		inputTouchButtons[i].calculateAbsolutePosition();
    	}*/
    	
    }
    
	public boolean onTouch( MotionEvent event) {
		
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
        pointerCount = event.getPointerCount();        
        int actionId = event.getPointerId(pointerIndex);
        
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){
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
        				Log.d(TAG, "Button was pushed: "+i);
        			}
        		}
        	}
        }
        
        if (action == MotionEvent.ACTION_MOVE) {
        	for (int i = 0; i < pointerCount; i++) {
                int pointerId = event.getPointerId(i);
            	points[pointerId] = new Point((int)event.getX(i), (int)event.getY(i));
        
  //              lastActions[pointerId] = action;
                if(pointerId == leftTouchIndex){
            		setLeftDelta(new Point((int)event.getX(pointerId)-lastLeftX,(int)event.getY(pointerId)-lastLeftY));
            		lastLeftX = (int)event.getX(pointerId);
            		lastLeftY = (int)event.getY(pointerId);
            	}
                if(pointerId == rightTouchIndex){
            		setRightDelta(new Point((int)event.getX(pointerId)-lastRightX,(int)event.getY(pointerId)-lastRightY));
            		lastRightX = (int)event.getX(pointerId);
            		lastRightY = (int)event.getY(pointerId);
            	}
            }
        //}
        }
        if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP ){
        	if(leftTouchIndex == actionId){
        		lastLeftX = 0;
        		lastLeftY = 0;
        		leftDelta = new Point(0,0);
        		leftTouchIndex = -1;
        	}
        	if(rightTouchIndex == actionId){
        		lastRightX = 0;
        		lastRightY = 0;
        		rightDelta = new Point(0,0);
        		rightTouchIndex = -1;
        	}
        }
        //touchView.invalidate();
        return true;
    }

	private void setLeftDelta(Point newleftDelta) {
		this.leftDelta = newleftDelta;
		
	}

	public Point getLeftDelta() {
		return leftDelta;
	}

	private void setRightDelta(Point newrightDelta) {
		this.rightDelta = newrightDelta;
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
	public boolean getButtonWasPressed(int number) {
		if(number<BUTTON_COUNT){
			if(inputTouchButtonWasPressed[number]){
				inputTouchButtonWasPressed[number] = false;
				return true;
			}
			
		}
		return false;
	}

}
