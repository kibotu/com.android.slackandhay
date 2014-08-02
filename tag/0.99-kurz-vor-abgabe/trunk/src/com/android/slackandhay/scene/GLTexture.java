package com.android.slackandhay.scene;

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
