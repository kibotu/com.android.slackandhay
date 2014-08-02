package com.android.slackandhay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOGameObjectFactory;
import com.android.slackandhay.gameobject.GOState.StateType;
import com.android.slackandhay.grid.GridWorld;
import com.android.slackandhay.input.InputSystem;
import com.android.slackandhay.sound.SoundEngine;

public class TestView extends View {

	private InputSystem inputSystem;
	private final SoundEngine soundEngine;

	private static final String TAG = "TestView";

	//<trash>
	private final GOGameObject pl;
	//</trash

	public TestView(final Context context) {
		super(context);
		soundEngine = SoundEngine.getInstance();
		soundEngine.loadSound(context);
		final GridWorld grid = new GridWorld(256, 256, new PointF(0,0), 30);
		final GOGameObjectFactory gofa = new GOGameObjectFactory(grid);
		pl = gofa.createPlayer();
	}

	//TODO
	//####################################################
	//# ON SIZE CHANGED _HAS_ TO BE IN PRODUCTION, LATER #
	//####################################################
	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "View Resolution is currently: "+getWidth()+"x"+getHeight() );
		if(inputSystem != null){
			inputSystem.setResolution(w, h);
		}
	}
	//TODO
	//####################################################
	//#  setInputSystem _HAS_ TO BE IN PRODUCTION, LATER #
	//####################################################
	public void setInputSystem(final InputSystem inputSystem){
		this.inputSystem = inputSystem;
	}

	@Override
	protected void onDraw(final Canvas canvas) {
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
