package com.android.slackandhay;

import android.os.SystemClock;
import android.util.Log;

public class GameThread implements Runnable {

	private static Thread _gmThread;

	private boolean _isRunning;
	private boolean _isPaused;
	private final long FPS = 1000 / 30;// FPS

	public GameThread() {
		super();
		Log.i("GameThread: ", "construct");
	}

	private void doLogic() {
//		Log.i("GameThread: ", "running");
		// go.update()
	}

	/**
	 * Main Game Loop
	 * 
	 * @Override
	 */
	public void run() {
		long startTime = SystemClock.uptimeMillis();
		while (_isRunning) {
//			_renderer.waitDrawingComplete();
			if (!_isPaused) {
				doLogic();
			}

			/** sleep if faster than desired framerate **/
			final long endTime = SystemClock.uptimeMillis();
			long finalDelta = endTime - startTime;
			if (finalDelta < FPS) {
				try {
					_gmThread.sleep(FPS - finalDelta);
				} catch (InterruptedException e) {
					// Interruptions here are no big deal.
				}
			}
			startTime = SystemClock.uptimeMillis();
		}
	}

	public void start() {
		_isRunning = true;
		_gmThread = new Thread(this);
		_gmThread.start();
	}

	public void stop() {
		_isRunning = false;
	}
}