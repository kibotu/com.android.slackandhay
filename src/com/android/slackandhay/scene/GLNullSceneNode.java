package com.android.slackandhay.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Ein Knoten, der selbst nichts rendert und nur zu organisatorischen Zwecken verwendet wird
 * 
 *
 */
public class GLNullSceneNode extends GLSceneNode {

	/**
	 * Ignoriert
	 */
	@Override
	protected boolean renderInternal(GL10 gl) {
		return true;
	}

	@Override
	protected void setupNode(GL10 gl) {
	}

	@Override
	protected void cleanupNode(GL10 gl) {
	}

	@Override
	protected void nextFrame(long dt) {
	}
}
