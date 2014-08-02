package com.android.slackandhay.scene;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Texture Loader Class
 * 
 * Basically loads Textures
 * 
 * @author Til Börner, Tom Wallroth, Jan Rabe
 *
 */
public class GLTextureLoader {

	private static final String TAG = GLTextureLoader.class.getSimpleName();
	private final Context _context;
	private final int [] textures;
	BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

	public GLTextureLoader(final Context context) {
		_context = context;
		textures = new int[1];
		sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	/**
	 * loads textures by resource Id and binds it to a specific id
	 * unpredicted behaviour for textures if their dimension is not a power of 2
	 * 
	 * @param gl
	 * @param resourceId
	 * @return generated texture id
	 */
	public int load(final GL10 gl, final int resourceId) {

		// Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		final Bitmap bitmap = BitmapFactory.decodeResource(_context.getResources(),resourceId,sBitmapOptions);
		Log.i(TAG, "[w:"+bitmap.getWidth()+"|h:"+bitmap.getHeight()+"|id:"+textures[0]+"] Bitmap successfully loaded.");

		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		//	Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Clean up
		bitmap.recycle();

		return textures[0];
	}
}
