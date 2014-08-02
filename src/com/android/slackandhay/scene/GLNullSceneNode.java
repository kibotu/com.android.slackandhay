package com.android.slackandhay.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * A Scene Node, that does not render itself but is there for organisation purposes.
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
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
