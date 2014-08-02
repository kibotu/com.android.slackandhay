package com.android.slackandhay.scene;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.slackandhay.R;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.SystemClock;
import android.util.Log;

public class GLRenderer implements Renderer {

	/**
	 * Scene Graph
	 */
	private final GLRootSceneNode _graph = new GLRootSceneNode();
	private Context _context;
	private long startTime = SystemClock.uptimeMillis();
	private final long FPS = 1000 / 30;
	private float angle = 0;

	public GLRenderer(Context context) {
		_context = context;
		Log.i("GLRenderer:","constructing...");
	}

	/**
	 * Erzeugt den Scene Graph
	 */
	private void createScene(GL10 gl) {
		if (_graph.getChildCount() > 0)
			return;
		Log.i("GLRenderer:","creating Scene...");

		// gc call
		Runtime r = Runtime.getRuntime();
        r.gc();
        
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(0, 0, -15).setDimension(0.43f, 1f, 0f));
		
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(-3f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(-2f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(-1f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(0f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(1f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(2f, 0, -10).setDimension(0.43f, 1f, 0f));
		_graph.add(new GLGridMeshNode(_context, R.drawable.crono).setPosition(3f, 0, -10).setDimension(0.43f, 1f, 0f));
		
	}

	
	/**
	 * Render Loop
	 */
	public void onDrawFrame(GL10 gl) {
		// Log.i("RendererThread: ", "running");
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Depth Buffering aktivieren
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// Culling aktivieren
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glFrontFace(GL10.GL_CCW);

		// Ein wenig Bewegung
		angle = (angle + 0.05f) % 360f;
		_graph.getChild(0).setPosition((float) Math.sin(angle) * 2, 0,
				(float) Math.cos(angle) * 9 - 10);
		
		// Wieviel Zeit ist seit dem letzten Rendern vergangen?
		final long endTime = SystemClock.uptimeMillis();
		long finalDelta = endTime - startTime;
		
		// Setze VBO-Texture Id
		for(int i = 0; i < _graph.getChildCount(); i++) {
			_graph.getChild(i).nextFrame(finalDelta);	
		}
		
		/** sleep if faster than desired framerate **/
		if (finalDelta < FPS) {
			try {
				// Log.i("GLRenderer:","sleeping..." +(FPS - finalDelta));
				Thread.sleep(FPS - finalDelta);
			} catch (InterruptedException e) {
				// Interruptions here are no big deal.
				Log.i("GLRenderer:",(FPS - finalDelta)+"s since last render - a little bit behind the schedule. Not a big deal though.");
			}
		}
		
		// Und los.
		_graph.render(gl);
		
		// startZeit neu setzen nach dem Rendern
		startTime = SystemClock.uptimeMillis();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		
		// Erzeugt die Szene
		createScene(gl);

		// Scene Graph initialisieren
		_graph.setup(gl);
	}
}