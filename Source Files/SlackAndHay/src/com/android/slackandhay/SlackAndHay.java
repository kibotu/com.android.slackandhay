package com.android.slackandhay;

import java.util.concurrent.Semaphore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.slackandhay.input.InputSystem;
import com.android.slackandhay.scene.GLSurfaceView;

/**
 * The actual slacknhay Activity that starts the game thread and the render thread
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 *
 */
public class SlackAndHay extends Activity {

	private GLSurfaceView _view;
	private GameThread _gameThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("SlackAndHayMain: ", "\n\nGAME STARTS\n\n\n");

		/** run garbage collector before starting **/
		final Runtime r = Runtime.getRuntime();
		r.gc();

		// first in first out fairness between threads (no guaranty though)
		final Semaphore semaphore = new Semaphore(1, true);
		/** RENDERER **/
		_view = new GLSurfaceView(this, semaphore);
		/**
		 * Setting InputSystem as touch input for the gl surface view
		 */
		_view.setOnTouchListener(InputSystem.getInstance());
		// start render thread
		setContentView(_view);
		/** GAME **/
		_gameThread = new GameThread(this, semaphore);
		// start thread
		_gameThread.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		RenderSychronizer.getInstance().reset();
		_gameThread.onPause();
		_view.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		RenderSychronizer.getInstance().reset();
		_gameThread.onResume();
		_view.onResume();
	}
}