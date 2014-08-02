package com.android.slackandhay.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.Log;

import com.android.slackandhay.R;
import com.android.slackandhay.RenderSychronizer;
import com.android.slackandhay.gameobject.GOGameObject;
import com.android.slackandhay.gameobject.component.GOComponentGraphics;
import com.android.slackandhay.gameobject.component.GOComponentType;
import com.android.slackandhay.input.InputSystem;

/**
 * Render Thread and Renderer
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
 *
 */
final public class GLSurfaceView extends android.opengl.GLSurfaceView implements android.opengl.GLSurfaceView.Renderer {

	private static final String TAG = GLSurfaceView.class.getSimpleName();
	/** context **/
	private final Context _context;
	/** semaphore **/
	private final Semaphore _semaphore;
	/** fps limit **/
	private final long FPS = 1000 / 30;
	/** start time of each frame **/
	private long startTime = SystemClock.uptimeMillis();
	/** scene graph **/
	private final GLRootSceneNode _sceneGraph = new GLRootSceneNode();
	/** controll scene graph **/
	private final GLRootSceneNode _hudGraph = new GLRootSceneNode();
	/** scene graph camera **/
	private GLCameraSceneNode _sceneCamera = new GLCameraSceneNode();
	/** hud camera **/
	private GLCameraSceneNode _hudCamera = new GLCameraSceneNode();
	/** glmeshes **/
	private final Vector<GOGameObject> _gameObjects;
	/** texture loader **/
	private final GLTextureLoader textureLoader;
	/** texture ids **/
	private final Vector<Integer> texturesIds;
	/** vboids **/
	private final Vector<Integer> vboIds;
	
	/** light **/
	private final boolean light = false;
	
	private final float[] lightSpot = {0.5f, 0.5f, 0.5f, 1f};
	private final float[] lightSpotPosition = {0f, 0f, 0f, 1f};
	private FloatBuffer lightSpotBuffer;
	private FloatBuffer lightSpotPositionBuffer;
	
	private float[] lightAmbient = {2f, 2f, 2f, 1.0f};
	private float[] lightDiffuse = {2f, 2f, 2f, 1f};
	private float[] specularLight = {1f, 1f, 1f, 1.0f };
	private float[] lightPosition = { 0f, 0f, 0f, 1f};
		
	/* The buffers for our light values */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer specularLightBuffer;
	private FloatBuffer lightPositionBuffer;
		
	/** fog **/
	private int fogFilter = 1;	//Which Fog To Use 
	/*
	 * Init the three fog filters we will use
	 * and the fog color
	 */
	private int fogMode[]= { GL10.GL_EXP, GL10.GL_EXP2,	GL10.GL_LINEAR };		
	private float[] fogColor = {0.5f, 0.5f, 0.5f, 0.5f};
	private FloatBuffer fogColorBuffer;	//The Fog Color Buffer
	private boolean fogEnabled = false;
	
	public GLSurfaceView(final Context context, final Semaphore semaphore) {
		super(context);
		_semaphore = semaphore;
		_context = context;
		_gameObjects = RenderSychronizer.getInstance().getGOGameObjects();
		texturesIds = new Vector<Integer>();
		vboIds = new Vector<Integer>();
		textureLoader = new GLTextureLoader(_context);
		initLight();
		setRenderer(this);
	}
	
	/**
	 * initializes light buffers
	 * 
	 */
	private void initLight() {

		// spot light
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightSpot.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightSpotBuffer = byteBuf.asFloatBuffer();
		lightSpotBuffer.put(lightSpot);
		lightSpotBuffer.position(0);
		
		// spot light position
		byteBuf = ByteBuffer.allocateDirect(lightSpotPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightSpotPositionBuffer = byteBuf.asFloatBuffer();
		lightSpotPositionBuffer.put(lightSpotPosition);
		lightSpotPositionBuffer.position(0);
		
		// ambient light
		byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);
		
		// diffuse light
		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);
		
		// specular light
		byteBuf = ByteBuffer.allocateDirect(specularLight.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		specularLightBuffer = byteBuf.asFloatBuffer();
		specularLightBuffer.put(specularLight);
		specularLightBuffer.position(0);
		
		// light position
		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);
		
		// fog
		byteBuf = ByteBuffer.allocateDirect(fogColor.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fogColorBuffer = byteBuf.asFloatBuffer();
		fogColorBuffer.put(fogColor);
		fogColorBuffer.position(0);
	}

	/**
	 * - setups scene graph
	 * - sets light
	 * - loads textures
	 * - setups buffers
	 * - sets active cameras
	 * - sets hud 
	 */
	private void  createScene(final GL10 gl) {
		if (_sceneGraph.getChildCount() > 0)
			return;

		// add all buffers
		final GLBufferLibrary bufferLibrary = GLBufferLibrary.getInstance();

		texturesIds.add(textureLoader.load(gl, R.drawable.crono));
		texturesIds.add(textureLoader.load(gl, R.drawable.hund512));
		texturesIds.add(textureLoader.load(gl, R.drawable.grass2));
		texturesIds.add(textureLoader.load(gl, R.drawable.soldat512));
		texturesIds.add(textureLoader.load(gl, R.drawable.vintagebg));
		texturesIds.add(textureLoader.load(gl, R.drawable.stonebg));

		// allocate and bind buffers and texture vbo ids
		vboIds.addAll(bufferLibrary.bindTextureVBOids(gl));

		// add game objects to scene graph
		for(int i = 0; i < _gameObjects.size(); i++) {
			Log.i(TAG,"Adding to scene graph go-id: "+ _gameObjects.get(i).getUID());
			final GLMeshNode node = (GLMeshNode)((GOComponentGraphics)_gameObjects.get(i).getComponent(GOComponentType.GRAPHICS)).getNode();
			node.setTextureId(texturesIds);
			node.setTextureVboId(vboIds);
			if(node.getChildCount() > 0) {
				GLSceneNode child = node.getChild(0);
				if(child instanceof GLCameraSceneNode) {
					_sceneCamera = (GLCameraSceneNode) child;
					_sceneGraph.setActiveCamera(_sceneCamera);
					Log.i(TAG, "camera added at [x="+_sceneCamera.getX() +"|y=" + _sceneCamera.getY() + "|z="+_sceneCamera.getZ()+ "]");
				}
			} else {
				_sceneGraph.add(node);
			}
		}
		
		_hudGraph.setActiveCamera(_hudCamera);
		_hudCamera.setPosition(-0.5f, 0, 0.5f, 0, 0, -1, 0, 1, 0);
		
		GLMeshNode attackButton = new GLMeshNode();
		attackButton.proposeTexture(Texture.CRONO);
		attackButton.setAbsPosition(0.6f, 0.5f, -1);
		attackButton.setScale(3f, 3f, 0);
		attackButton.setTextureVboOffset(Animation.GUI_WALK.values[0]*32);
		attackButton.setTextureId(texturesIds);
		attackButton.setTextureVboId(vboIds);
		
		GLMeshNode walkButton = new GLMeshNode();
		walkButton.proposeTexture(Texture.CRONO);
		walkButton.setAbsPosition(0.6f, 0f, -1);
		walkButton.setScale(3f, 3f, 0);
		walkButton.setTextureVboOffset(Animation.GUI_SWORD.values[0]*32);
		walkButton.setTextureId(texturesIds);
		walkButton.setTextureVboId(vboIds);
		
		GLMeshNode defendButton = new GLMeshNode();
		defendButton.proposeTexture(Texture.CRONO);
		defendButton.setAbsPosition(0.6f, -0.5f, -1);
		defendButton.setScale(3f, 3f, 0);
		defendButton.setTextureVboOffset(Animation.GUI_SHIELD.values[0]*32);
		defendButton.setTextureId(texturesIds);
		defendButton.setTextureVboId(vboIds);
		
		_hudGraph.add(attackButton);
		_hudGraph.add(walkButton);
		_hudGraph.add(defendButton);
	}

//	Point p =  InputSystem.getInstance().getMapOffsetPoint();
	PointF p = new PointF(0,0);
	
	/**
	 * invoked every frame
	 * 
	 * Render Loop
	 */
	@Override
	public void onDrawFrame(final GL10 gl) {
		
		// aquire permit
		_semaphore.acquireUninterruptibly();
		
		//Clear Screen And Depth Buffer
//		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_STENCIL_BUFFER_BIT );
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );

		//Check if the light flag has been set to enable/disable lighting
		if(light) {
			gl.glEnable(GL10.GL_LIGHTING);
//			gl.glEnable(GL10.GL_NORMALIZE);
			gl.glEnable(GL10.GL_RESCALE_NORMAL);
			gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE,0);  						//sets lighting to one-sided
			gl.glPushMatrix();
			p.x += 0.01f;
			p.y += 0.01f;
			if(p.x > 5) {
				p.x = 0;
				p.y = 0;
			}
			gl.glTranslatef(p.x, p.y, 3f);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);	//Position The Light
			gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightSpotPositionBuffer);//Position The Light
			gl.glPopMatrix();
		} else {
			gl.glDisable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_NORMALIZE);
		}

		// Depth Buffering aktivieren
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// Culling aktivieren
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glFrontFace(GL10.GL_CCW);
		gl.glCullFace(GL10.GL_BACK);

		// Wieviel Zeit ist seit dem letzten Rendern vergangen?
		final long endTime = SystemClock.uptimeMillis();
		final long finalDelta = endTime - startTime;

		/** sleep if faster than desired framerate **/
		if (finalDelta < FPS) {
			try {
				// Log.i("GLRenderer:","sleeping..." +(FPS - finalDelta));
				Thread.sleep(FPS - finalDelta);
			} catch (final InterruptedException e) {
				// Interruptions here are no big deal.
				Log.i(TAG,FPS - finalDelta+"s since last render - a little bit behind the schedule. Not a big deal though.");
			}
		}
		
		// scene graph
		_sceneGraph.render(gl);
		
		// Disable Depth Testing
		gl.glDisable(GL10.GL_DEPTH_TEST); 
		
		gl.glPushMatrix();
		
		// hud
		_hudGraph.render(gl);
		
		gl.glPopMatrix();
		
		
		// Enables Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST); 			
		
		// startZeit neu setzen nach dem Rendern
		startTime = SystemClock.uptimeMillis();

		// release permit
		_semaphore.release();
	}

	/**
	 * invoked on viewport rotation
	 */
	@Override
	public void onSurfaceChanged(final GL10 gl, final int width, int height) {
		//Prevent A Divide By Zero By : Making Height Equal One
		height = height == 0 ? 1 : height;
		_sceneCamera.setDimension(width, height,0);
		_sceneCamera.switchToPerspView(gl);
		InputSystem.getInstance().setResolution(width, height);
	}

	/**
	 * invoked when renderer starts
	 */
	@Override
	public void onSurfaceCreated(final GL10 gl, final EGLConfig config) {

		gl.glEnable(GL10.GL_LIGHT0);								//Enable Light 0
//		gl.glEnable(GL10.GL_LIGHT1);								//Enable Light 1
		
//		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPOT_DIRECTION, lightSpotBuffer);		//Setup The Diffuse Light
//		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, lightSpotBuffer);
//		gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_CUTOFF, 80);
//		gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_EXPONENT, 10);

		
		//And there'll be light!
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);		//Setup The Ambient Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);		//Setup The Diffuse Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularLightBuffer); 	// Setup the Specular Light

		//Settings
		gl.glEnable(GL10.GL_DITHER);				//Enable dithering
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0f, 0f, 0f, 1f);			//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do

		if(fogEnabled ) {
			//The Fog/The Mist
			gl.glFogf(GL10.GL_FOG_MODE, fogMode[fogFilter]);	//Fog Mode
			gl.glFogfv(GL10.GL_FOG_COLOR, fogColorBuffer);		//Set Fog Color
			gl.glFogf(GL10.GL_FOG_DENSITY, 0.8f);				//How Dense Will The Fog Be
			gl.glHint(GL10.GL_FOG_HINT, GL10.GL_DONT_CARE);		//Fog Hint Value
			gl.glFogf(GL10.GL_FOG_START, -1f);					//Fog Start Depth
			gl.glFogf(GL10.GL_FOG_END, -1.01f);					//Fog End Depth
			gl.glEnable(GL10.GL_FOG);							//Enables GL_FOG
		} else {
			gl.glDisable(GL10.GL_FOG);
		}

		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		// add objects to graph
		createScene(gl);

		// Scene Graph initialisieren
		_sceneGraph.setup(gl);
		_hudGraph.setup(gl);
//		if(gl.glGetError() != 0) {
//			Log.e(TAG, ""+gl.glGetError());	
//		}
	}
}