package com.android.slackandhay;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.Log;

import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.GOGameObjectManager;
import com.android.slackandhay.grid.GridWorld;
import com.android.slackandhay.level.Level;
import com.android.slackandhay.sound.SoundEngine;
import com.android.slackandhay.sound.SoundEngine.Sounds;

final public class GameThread implements Runnable {

	@SuppressWarnings("unused")
	private static final String TAG = GameThread.class.getSimpleName();
	private static final boolean DISPLAY_FRAMERATE_ON_LOGCAT = false;
	private static Thread _gmThread;

	private boolean _isRunning;
	private boolean _isPaused;
	private final Semaphore _semaphore;
	private final Vector<GOGameObject> _gameObjects = RenderSychronizer
			.getInstance().getGOGameObjects();
	private final Context _context;

	public GameThread(final Context context, final Semaphore semaphore) {
		super();
		_context = context;
		_semaphore = semaphore;
		init();
	}

	/**
	 * game init
	 */
	private void init() {
		final Level level = new Level(_context);
		final GridWorld grid = level.load(R.raw.world);

		final GOGameObjectManager gomanager = new GOGameObjectManager(grid,level);
		gomanager.createGameObjects();

		// TODO kill verbose messages somehow
		SoundEngine.getInstance().loadSound(_context);
	}

	/**
	 * game logic loop
	 */
	private void doLogic(final int dt) {
		final int gameobjectcount = _gameObjects.size();
		for (int i = 0; i < gameobjectcount; i++) {
			_gameObjects.get(i).update(dt);
		}
	}

	private int fps = 0;
	private int secondLength = 0;
	private final int desiredMaxFrameRate = 60;

	/**
	 * Main Game Loop
	 * 
	 * @Override
	 */
	@Override
	public void run() {
		long startTime = SystemClock.uptimeMillis();
		while (_isRunning) {

			/** aquire permit **/
			_semaphore.acquireUninterruptibly();

			final long finalDelta = SystemClock.uptimeMillis() - startTime;

			if (DISPLAY_FRAMERATE_ON_LOGCAT) {
				secondLength += finalDelta;
				fps++;
				if (secondLength > 1000) {
					Log.v(TAG, "Current Framerate: " + fps);
					secondLength = 0;
					fps = 0;
				}
			}

			if (finalDelta < 1000 / desiredMaxFrameRate) {
				try {
					synchronized (this) {
						this.wait(1000 / desiredMaxFrameRate - finalDelta);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


			/** do logic **/
			if (!_isPaused) {
				doLogic((int) finalDelta);
			}

			/** reset start time **/
			startTime = SystemClock.uptimeMillis();

			/** release permit **/
			_semaphore.release();
		}
	}

	public void onStart() {
		_isRunning = true;
		_gmThread = new Thread(this);
		_gmThread.start();
	}

	public void onResume() {
		_isPaused = false;
	}

	public void onPause() {
		_isPaused = true;
	}

	public void onStop() {
		_isRunning = false;
	}
}