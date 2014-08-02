package com.android.slackandhay;

import com.android.slackandhay.scene.GLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.android.slackandhay.input.InputSystem;

public class SlackAndHayMain extends Activity {

	private static int nextUID = 0;
	private GLSurfaceView _view;
	private GameThread _gameThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("SlackAndHayMain: ", "construct");
		 // no title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// fullscreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/** GAME **/
		_gameThread = new GameThread();
		/** RENDERER **/
		_view = new GLSurfaceView(this);
		InputSystem inputSystem = new InputSystem();
		_view.setInputSystem(inputSystem);
		_view.setOnTouchListener(inputSystem);
		start();
	}
	
	public void start() {
		/** start garbage collector before starting **/
        Runtime r = Runtime.getRuntime();
        r.gc();
        // start render thread
		setContentView(_view);
		// start  thread  
//		_gameThread.start();
		
		/**
		 * GAME LOGIC HERE
		 */
	}

	public static int getNewUID() {
		nextUID++;
		return nextUID;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		_gameThread.stop();
		_view.onPause();
	}
}