package com.android.slackandhay.scene;

/**
 * Container Class for holding runtime needed variables of textures
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
 * 
 */

public class GLTexture {

	public float width;
	public float height;
	public int offsetId;
	public boolean isLoaded;

	public GLTexture(final float width, final float height, final int offsetId) {
		this.width = width;
		this.height = height;
		this.offsetId = offsetId;
		isLoaded = false;
	}
}
