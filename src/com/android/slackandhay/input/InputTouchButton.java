package com.android.slackandhay.input;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;


/**
 * This class is used to define Buttons on the touch screen
 * The Buttons are defined independently from the screen resolution.
 * Thats why the position is somewhere between 0f and 1f.
 * 
 * eg if the horizontal resolution is 1024px and the button position is set to
 * 0.5f, then the button begins at 512px.
 * 
 * @author tom
 *
 */
public class InputTouchButton {
	private PointF relativePosition = new PointF(0,0);
	private PointF relativeSize = new PointF(0,0);
	private Point pixelPosition = new Point(0,0);
	private Point pixelSize = new Point(0,0);
	private InputTouch inputTouch;
	
	public InputTouchButton(InputTouch inputTouch, float x, float y, float width, float height){
		this.inputTouch = inputTouch;
		this.relativePosition.x = x;
		this.relativePosition.y = y;
		this.relativeSize.x = width;
		this.relativeSize.y = height;
		calculateAbsolutePosition();
	}
	public void calculateAbsolutePosition(){
		pixelPosition.x = (int)(inputTouch.getScreenResolutionX()*relativePosition.x);
		pixelPosition.y = (int)(inputTouch.getScreenResolutionY()*relativePosition.y);
		pixelSize.x = (int)(inputTouch.getScreenResolutionX()*relativeSize.x);
		pixelSize.y = (int)(inputTouch.getScreenResolutionY()*relativeSize.y);
	}
	public boolean wasPushed(int x, int y){
		return ( x>=(pixelPosition.x) && x<=(pixelPosition.x+pixelSize.x) &&
				y>=(pixelPosition.y) && y<=(pixelPosition.y+pixelSize.y) );
	}
}
