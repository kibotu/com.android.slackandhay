package com.android.slackandhay;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOGameObjectFactory;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.input.InputSystem;
import com.android.slackandhay.sound.SoundEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class TestView extends View {
	
	private InputSystem inputSystem;
	private SoundEngine soundEngine;
	
	private static final String TAG = "TestView";
	
	//<trash>
	private GOGameObject pl;
	//</trash

    public TestView(Context context) {
    	super(context);
    	soundEngine = new SoundEngine(context);
    	GOGameObjectFactory gofa = new GOGameObjectFactory();
        pl = gofa.createPlayer();
    }
    
    //TODO    
    //####################################################
    //# ON SIZE CHANGED _HAS_ TO BE IN PRODUCTION, LATER #
    //####################################################
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "View Resolution is currently: "+this.getWidth()+"x"+this.getHeight() );
    	if(inputSystem != null){
    		inputSystem.setResolution(w, h);
    	}
    }
    //TODO
    //####################################################
    //#  setInputSystem _HAS_ TO BE IN PRODUCTION, LATER #
    //####################################################
    public void setInputSystem(InputSystem inputSystem){
    	this.inputSystem = inputSystem;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(inputSystem.buttonWasPressed(0)){
        	//soundEngine.playSound(SoundEngine.Sounds.HIT_CONCRETE);
        	pl.update(10);
        	Log.d(TAG, "Triggered Player update...");
        }
        if(inputSystem.buttonWasPressed(1)){
        	pl.getStateMananger().proposeState(StateType.ATTACKING);
        	//soundEngine.playSound(SoundEngine.Sounds.HIT_FLESH);
        }
        if(inputSystem.buttonWasPressed(2)){
        	//soundEngine.playSound(SoundEngine.Sounds.HIT_SWORD);
        }
        this.invalidate();
       }
}
