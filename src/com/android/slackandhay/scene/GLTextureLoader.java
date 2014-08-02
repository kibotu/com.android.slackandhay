package com.android.slackandhay.scene;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GLTextureLoader {
	
	private Context _context;
	
	public GLTextureLoader(Context context) {
		_context = context;
	}
	
	public Bitmap load(int resourceId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = 240;
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(_context.getResources(), resourceId, options);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i("GLTextureLoader", "Bitmap:{w:" + width + " h:" + height + "}");
		return bitmap;
	}
}