package com.android.slackandhay.scene;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class GLGridMeshNode extends GLSceneNode{
	
	private short indicesSize;

	/**
	 * Die ID des VBO
	 */
	private int _vertexVboId;
	
	/**
	 * Die ID des Index-VBO
	 */
	private int _indexVboId;
	
	/**
	 * Die ID des Texture-VBO
	 */
	private int [] _textureVboId;
	
	/**
	 * context reference for loading
	 */
	private Context _context;

	/**
	 * Our texture pointer 
	 */
	private int[] textures = new int[1];
	
	/**
	 * texure resourceid
	 */
	private int _resourceId;

	/**
	 * current Frame
	 */
	private int _curFrame;
	
	public GLGridMeshNode(Context context, int resourceId) {
		super();
		_context = context;
		_resourceId = resourceId;
		_curFrame = 0;
	}
	
	public boolean renderInternal(GL10 gl) {
		
		// OpenGL 1.1-Instanz beziehen
		GL11 gl11 = (GL11)gl;
		
		// enable texture
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		// alpha blending 
		gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL11.GL_BLEND);
		
		// kill alpha fragments
		gl.glAlphaFunc(GL11.GL_GREATER,0.1f);
		gl.glEnable(GL11.GL_ALPHA_TEST);				
		
		// VBO binden
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _vertexVboId);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, _indexVboId);

		// Vertex Array-State aktivieren und Vertexttyp setzen
		gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Zeichne Vertices!
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		// load and bind texture
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _textureVboId[_curFrame]);
		
		// Zeichne Texturkoordinaten
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		
		// Zeichne Triangles
		assert(indicesSize > 0);
		gl11.glDrawElements(GL11.GL_TRIANGLES, indicesSize, GL11.GL_UNSIGNED_SHORT, 0);

		// disable texture
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		// disable blending		
		gl.glDisable(GL11.GL_BLEND);
		gl.glDisable(GL11.GL_ALPHA_TEST);
		
		// Vertex Array-State deaktivieren
		gl11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Puffer abwählen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

		return true;
	}

	@Override
	protected void setupNode(GL10 gl) {
		
		loadTexture(gl,_resourceId);
		
		// Vertices erzeugen
		float[] vertices = new float[4*3];
		vertices[ 0] =  0.5f; vertices[ 1] =  0.5f; vertices[ 2] =  0.0f; // 0
		vertices[ 3] = -0.5f; vertices[ 4] =  0.5f; vertices[ 5] =  0.0f; // 1
		vertices[ 6] = -0.5f; vertices[ 7] = -0.5f; vertices[ 8] =  0.0f; // 2 
		vertices[ 9] =  0.5f; vertices[10] = -0.5f; vertices[11] =  0.0f; // 3
		
		// Indizes erzeugen
		short[] indices = new short[] {
				0,1,2,2,3,0
		};
		indicesSize = (short) indices.length;
		
		// texture erzeugen
//		float [] texture = new float [] {
//    			  	1.0f, 0.0f,
//                  0.0f, 0.0f,
//                  0.0f, 1.0f,
//                  1.0f, 1.0f,
//		};
		
		float [] textureCoordinates = new float[] {
				// zwinkern
				16, 22, 32, 70,   // 0
				56, 22, 32, 70,   // 1
				96, 22, 32, 70,   // 2
				// walking down
				170, 22, 36, 70,  // 3
				214, 22, 32, 70,  // 4
				256, 22, 34, 70,  // 5
				296, 22, 36, 70,  // 6
				336, 22, 32, 70,  // 7
				376, 22, 34, 70,  // 8
				// running down
				676, 18, 36, 70,  // 9
				722, 18, 36, 70,  // 10
				// umschauen
				976, 16, 32, 70,  // 11
				1016, 16, 32, 70, // 12
				// walking up
				94, 694, 36, 70,  // 13
				146, 690, 32, 70, // 14
				190, 696, 36, 70, // 15
				234, 694, 36, 70, // 16
				280, 690, 32, 70, // 17
				322, 696, 32, 70, // 18
				// running up
				602, 690, 34, 76, // 19
				644, 690, 34, 76, // 20
				// walking right
				154, 364, 30, 68, // 21
				202, 368, 42, 64, // 22
				256, 366, 28, 66, // 23
				300, 364, 28, 68, // 24
				344, 368, 44, 64, // 25
				402, 366, 28, 66, // 26
				// running right
				738, 374, 56, 64, // 27
				802, 374, 56, 64, // 28
				// walking left
//				flip(getSubImage(154, 364, 30, 68)), // 29
//				flip(getSubImage(202, 368, 42, 64)), // 30
//				flip(getSubImage(256, 366, 28, 66)), // 31
//				flip(getSubImage(300, 364, 28, 68)), // 32
//				flip(getSubImage(344, 368, 44, 64)), // 33
//				flip(getSubImage(402, 366, 28, 66)), // 34
//				// running left
//				flip(getSubImage(738, 374, 56, 64)), // 35
//				flip(getSubImage(802, 374, 56, 64)), // 36
		};
		
		// Byte-Puffer für Vertex Buffer erzeugen
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); // 4 byte per float
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = vbb.asFloatBuffer();

		// Byte-Puffer für Index Buffer erzeugen
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2); // 4 byte per short
		ibb.order(ByteOrder.nativeOrder());
		ShortBuffer indexBuffer = ibb.asShortBuffer();
		
		FloatBuffer [] textureBuffers = getTextures(textureCoordinates);
		_textureVboId = new int[textureBuffers.length];
		Log.i("GLGridMeshNode:", "_textureVboId length: "+_textureVboId.length);
		
		// Daten laden
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		// OpenGL 1.1-Instanz beziehen
		GL11 gl11 = (GL11)gl;

		// VBO erzeugen, ID ermitteln
		IntBuffer buffer = IntBuffer.allocate(2+textureBuffers.length);
		gl11.glGenBuffers(2+textureBuffers.length, buffer);
		_vertexVboId = buffer.get(0);
		_indexVboId = buffer.get(1);

		for(int i = 0; i < textureBuffers.length; i++) {
			_textureVboId[i] = buffer.get(i+2);
			 // texture 2
	        gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _textureVboId[i]);
	        gl11.glBufferData(GL11.GL_ARRAY_BUFFER, 8 * 4, textureBuffers[i], GL11.GL_STATIC_DRAW);
		}
		
		// Vertex-VBO binden und beladen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, _vertexVboId);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GL11.GL_STATIC_DRAW);
		
		// Index-VBO binden und beladen
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, _indexVboId);
		gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indices.length * 2, indexBuffer, GL11.GL_STATIC_DRAW);
        
		// Puffer abwählen
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		// Vertex Buffer wegwerfen
		vertexBuffer.clear();
		indexBuffer.clear();
		for(int i = 0; i < textureBuffers.length; i++) {
			textureBuffers[i].clear();
		}
		
	}
	
	/**
	 * coords[] to floatbuffer[]
	 * @param coords[]
	 * x,y,width,height per texture coordinate
	 */
	private FloatBuffer[] getTextures(float[] coords) {
		assert(coords != null);
		assert(coords.length % 4 == 0);
		int amount = coords.length / 4;
		FloatBuffer [] result = new FloatBuffer[amount];
		
		for(int i = 0; i < amount; i++) {
			// build coords
			float [] texture = new float [] {
					1f/1191f*(coords[0+(i*4)]+coords[2+(i*4)]), 1f/1130f*coords[1+(i*4)],
					1f/1191f*coords[0+(i*4)], 1f/1130f*coords[1+(i*4)],
					1f/1191f*coords[0+(i*4)], 1f/1130f*(coords[1+(i*4)]+coords[3+(i*4)]),
					1f/1191f*(coords[0+(i*4)]+coords[2+(i*4)]), 1f/1130f*(coords[1+(i*4)]+coords[3+(i*4)]),
			};
			
			//  Byte-Puffer für Textur Buffer erzeugen
			ByteBuffer byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			FloatBuffer textureBuffer = byteBuf.asFloatBuffer();
			textureBuffer.put(texture);
			textureBuffer.position(0);
			result[i] = textureBuffer;
		}
		return result;
	}

	@Override
	protected void cleanupNode(GL10 gl) {

		// OpenGL 1.1-Instanz beziehen
		GL11 gl11 = (GL11)gl;
		
		// Puffer aufräumen
		gl11.glDeleteBuffers(2, new int [] { _vertexVboId, _indexVboId,}, 0);
		gl11.glDeleteBuffers(_textureVboId.length, _textureVboId, 0);
	}

	@Override
	protected void nextFrame(long dt) {
		if(_curFrame > _textureVboId.length-2) {
			_curFrame = 0;
		} else {
			_curFrame++;
		} 
	}
	
	public void loadTexture(GL10 gl, int resourceId) {
		//Get the texture from the Android resource directory
		InputStream is = _context.getResources().openRawResource(resourceId);
		BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap bitmap = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is, null, sBitmapOptions);
			Log.i("GLGridMeshNode: ", "[w:"+bitmap.getWidth()+"|h:"+bitmap.getHeight()+"] Bitmap successfully loaded.");
		} finally {
			//Always clear and close
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}

		//Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		//...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		//Clean up
		bitmap.recycle();
	}
}
