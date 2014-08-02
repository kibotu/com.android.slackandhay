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
	protected boolean renderInternal(final GL10 gl) {
		return true;
	}

	@Override
	protected void setupNode(final GL10 gl) {
	}

	@Override
	protected void cleanupNode(final GL10 gl) {
	}
}
