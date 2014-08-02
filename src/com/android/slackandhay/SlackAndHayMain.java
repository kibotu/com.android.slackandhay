package com.android.slackandhay;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOGameObjectFactory;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.input.InputSystem;

import android.app.Activity;
import android.os.Bundle;

public class SlackAndHayMain extends Activity {
	
	//TODO Wer verteilt die UIDs? Ist das toll so?
	private static int nextUID = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputSystem inputSystem = new InputSystem();
        
        TestView touchView = new TestView(this);
        touchView.setInputSystem(inputSystem);
        touchView.setOnTouchListener(inputSystem);
        setContentView(touchView);
        
    
    }
    
    public static int getNewUID(){
    	nextUID++;
    	return nextUID;
    }
}