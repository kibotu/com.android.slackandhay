package com.android.slackandhay.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Root Scene Node
 * 
 * represents Scene Graph and is supposed to hold all scene nodes for a specific
 * scene; renders camera
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
 * 
 */
public class GLRootSceneNode extends GLNullSceneNode {

	/**
	 * Die aktive Kamera
	 */
	private GLCameraSceneNode _activeCamera = new GLCameraSceneNode();

	/**
	 * Setzt die aktive Kamera
	 * 
	 * @param camera
	 */
	public void setActiveCamera(final GLCameraSceneNode camera) {
		_activeCamera = camera;
	}

	@Override
	public void render(final GL10 gl) {

		// Model View setzen
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		// Kamera aktivieren
		_activeCamera.setActive(gl);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		// Internes Rendern
		super.render(gl);
	}
}