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

/**
 * This is the class in which all game logic related objects are instantiated
 * 
 * @author Tilman Börner, Jan Rabe & Tom Wallroth
 * 
 */
final public class GameThread implements Runnable {

	@SuppressWarnings("unused")
	private static final String TAG = GameThread.class.getSimpleName();
	private static final boolean DISPLAY_FRAMERATE_ON_LOGCAT = false;
	private static final boolean ENEABLE_FRAME_LIMITER = false;
	private static Thread _gmThread;

	private boolean _isRunning;
	private boolean _isPaused;
	private final Semaphore _semaphore;
	private final Vector<GOGameObject> _gameObjects = RenderSychronizer.getInstance().getGOGameObjects();
	private final Context _context;

	/**
	 * Creates a new GameThread
	 * 
	 * @param context
	 *            the android context
	 * @param semaphore
	 *            the semaphore used to synchronize the game thread and the
	 *            render thread
	 */
	public GameThread(final Context context, final Semaphore semaphore) {
		super();
		_context = context;
		_semaphore = semaphore;
		init();
	}

	/**
	 * The init method creates a world, the game object manager and loads the
	 * level, as well as the sound engine
	 */
	private void init() {
		final Level level = new Level(_context);
		final GridWorld grid = level.load(R.raw.world);

		final GOGameObjectManager gomanager = new GOGameObjectManager(grid, level);
		gomanager.createGameObjects();

		// TODO kill verbose messages somehow
		SoundEngine.getInstance().loadSound(_context);
	}

	/**
	 * this function lets the game progress and updates all the gameobjects
	 * 
	 * @param dt
	 *            the amount of milliseconds the game shall progress
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
	 * this method is run by the thread
	 */
	@Override
	public void run() {
		long startTime = SystemClock.elapsedRealtime();
		while (_isRunning) {

			/** aquire permit **/
			_semaphore.acquireUninterruptibly();

			final long finalDelta = SystemClock.elapsedRealtime() - startTime;

			startTime = SystemClock.elapsedRealtime();
			/** reset start time **/
			if (DISPLAY_FRAMERATE_ON_LOGCAT) {
				secondLength += finalDelta;
				fps++;
				if (secondLength > 1000) {
					Log.v(TAG, "Current Framerate: " + fps);
					secondLength = 0;
					fps = 0;
				}
			}

			if (ENEABLE_FRAME_LIMITER) {
				if (finalDelta < 1000 / desiredMaxFrameRate) {
					try {
						synchronized (this) {
							this.wait(1000 / desiredMaxFrameRate - finalDelta);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			/** do logic **/
			if (!_isPaused) {
				doLogic((int) finalDelta);
			}

			/** release permit **/
			_semaphore.release();
		}
	}

	/**
	 * This method is called by the Android API
	 */
	public void onStart() {
		_isRunning = true;
		_gmThread = new Thread(this);
		_gmThread.start();
	}

	/**
	 * This method is called by the Android API
	 */
	public void onResume() {
		_isPaused = false;
	}

	/**
	 * This method is called by the Android API
	 */
	public void onPause() {
		_isPaused = true;
	}

	/**
	 * This method is called by the Android API
	 */
	public void onStop() {
		_isRunning = false;
	}
}